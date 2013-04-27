package au.com.codeka.warworlds.server.ctrl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import au.com.codeka.common.protobuf.Messages;
import au.com.codeka.warworlds.server.RequestException;
import au.com.codeka.warworlds.server.data.DB;
import au.com.codeka.warworlds.server.data.SqlStmt;
import au.com.codeka.warworlds.server.data.Transaction;
import au.com.codeka.warworlds.server.model.Colony;
import au.com.codeka.warworlds.server.model.Empire;
import au.com.codeka.warworlds.server.model.Star;

public class EmpireController {
    private DataBase db;

    public EmpireController() {
        db = new DataBase();
    }
    public EmpireController(Transaction trans) {
        db = new DataBase(trans);
    }

    public Empire getEmpire(int id) throws RequestException {
        try {
            return db.getEmpires(new int[] {id}).get(0);
        } catch (SQLException e) {
            throw new RequestException(500, e);
        }
    }

    public List<Empire> getEmpires(int[] ids) throws RequestException {
        try {
            return db.getEmpires(ids);
        } catch (SQLException e) {
            throw new RequestException(500, e);
        }
    }

    public Empire getEmpireByEmail(String email) throws RequestException {
        try {
            return db.getEmpireByEmail(email);
        } catch (SQLException e) {
            throw new RequestException(500, e);
        }
    }

    public List<Empire> getEmpiresByName(String name, int limit) throws RequestException {
        try {
            return db.getEmpiresByName(name, limit);
        } catch (SQLException e) {
            throw new RequestException(500, e);
        }
    }

    public int[] getStarsForEmpire(int empireId) throws RequestException {
        try {
            return db.getStarsForEmpire(empireId);
        } catch (SQLException e) {
            throw new RequestException(500, e);
        }
    }

    public boolean withdrawCash(int empireId, float amount, Messages.CashAuditRecord.Builder audit_record_pb) throws RequestException {
        try (Transaction t = DB.beginTransaction()) {
            SqlStmt stmt = t.prepare("SELECT cash FROM empires WHERE id = ?");
            stmt.setInt(1, empireId);
            double cashBefore = stmt.selectFirstValue(Double.class);
            if (cashBefore <= amount) {
                return false;
            }

            audit_record_pb.setBeforeCash((float) cashBefore);
            audit_record_pb.setAfterCash((float) (cashBefore - amount));

            stmt = t.prepare("UPDATE empires SET cash = cash - ? WHERE id = ?");
            stmt.setDouble(1, amount);
            stmt.setInt(2, empireId);
            stmt.update();

            stmt = t.prepare("INSERT INTO empire_cash_audit (empire_id, cash_before, cash_after," +
                                                           " time, reason) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, empireId);
            stmt.setDouble(2, cashBefore);
            stmt.setDouble(3, cashBefore - amount);
            stmt.setDateTime(4, DateTime.now());
            stmt.setBlob(5, audit_record_pb.build().toByteArray());
            stmt.update();

            t.commit();
            return true;
        } catch(Exception e) {
            throw new RequestException(500, e);
        }
    }

    public void createEmpire(Empire empire) throws RequestException {
        if (empire.getDisplayName().trim().equals("")) {
            throw new RequestException(400, Messages.GenericError.ErrorCode.CannotCreateEmpireBlankName,
                                       "You must give your empire a name.");
        }

        NewEmpireStarFinder starFinder = new NewEmpireStarFinder();
        if (!starFinder.findStarForNewEmpire()) {
            throw new RequestException(500); // todo: expand universe
        }
        Star star = new StarController().getStar(starFinder.getStarID());
        empire.setHomeStar(star);

        // create the empire
        db.createEmpire(empire);

        // empty the star of it's current (native) inhabitants
        String sql = "DELETE FROM colonies WHERE star_id = ?";
        try (SqlStmt stmt = DB.prepare(sql)) {
            stmt.setInt(1, star.getID());
            stmt.update();
        } catch(Exception e) {
            throw new RequestException(500, e);
        }
        sql = "DELETE FROM fleets WHERE star_id = ?";
        try (SqlStmt stmt = DB.prepare(sql)) {
            stmt.setInt(1, star.getID());
            stmt.update();
        } catch(Exception e) {
            throw new RequestException(500, e);
        }

        colonize(empire, star, starFinder.getPlanetIndex());

        sql = "INSERT INTO empire_presences (empire_id, star_id, total_goods, total_minerals) VALUES (?, ?, ?, ?)";
        try (SqlStmt stmt = DB.prepare(sql)) {
            stmt.setInt(1, empire.getID());
            stmt.setInt(2, star.getID());
            stmt.setDouble(3, 500.0);
            stmt.setDouble(4, 500.0);
            stmt.update();
        } catch(Exception e) {
            throw new RequestException(500, e);
        }

        new FleetController(db.getTransaction()).createFleet(empire, star, "colonyship", 1.0f);
        new FleetController(db.getTransaction()).createFleet(empire, star, "scoute", 10.0f);

        // update the last simulation time for the star so that it doesn't simulate until we
        // actually arrived...
        sql = "UPDATE stars SET last_simulation = ?";
        try (SqlStmt stmt = DB.prepare(sql)) {
            stmt.setDateTime(1, DateTime.now());
            stmt.update();
        } catch(Exception e) {
            throw new RequestException(500, e);
        }
    }

    public Colony colonize(Empire empire, Star star, int planetIndex) throws RequestException {
        Colony colony = null;

        // add the initial colony and fleets to the star
        String sql = "INSERT INTO colonies (sector_id, star_id, planet_index, empire_id," +
                                          " focus_population, focus_construction, focus_farming," +
                                          " focus_mining, population, uncollected_taxes," +
                                          " cooldown_end_time)" +
             " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (SqlStmt stmt = db.prepare(sql, Statement.RETURN_GENERATED_KEYS)) {
            colony = new Colony(0, star.getSectorID(), star.getID(), planetIndex,
                    empire == null ? null : empire.getID());
            stmt.setInt(1, colony.getSectorID());
            stmt.setInt(2, colony.getStarID());
            stmt.setInt(3, colony.getPlanetIndex());
            if (colony.getEmpireKey() == null) {
                stmt.setNull(4);
            } else {
                stmt.setInt(4, colony.getEmpireID());
            }
            stmt.setDouble(5, colony.getPopulationFocus());
            stmt.setDouble(6, colony.getConstructionFocus());
            stmt.setDouble(7, colony.getFarmingFocus());
            stmt.setDouble(8, colony.getMiningFocus());
            stmt.setDouble(9, colony.getPopulation());
            stmt.setDouble(10, colony.getUncollectedTaxes());
            stmt.setDateTime(11, colony.getCooldownEndTime());
            stmt.update();
            colony.setID(stmt.getAutoGeneratedID());
        } catch(Exception e) {
            throw new RequestException(500, e);
        }

        // update the count of colonies in the sector
        if (empire != null) {
            sql = "UPDATE sectors SET num_colonies = num_colonies+1 WHERE id = ?";
            try (SqlStmt stmt = db.prepare(sql)) {
                stmt.setInt(1, star.getSectorID());
                stmt.update();
            } catch(Exception e) {
                throw new RequestException(500, e);
            }
        }

        star.getColonies().add(colony);
        return colony;
    }

    private static class DataBase extends BaseDataBase {
        public DataBase() {
            super();
        }
        public DataBase(Transaction trans) {
            super(trans);
        }

        public void createEmpire(Empire empire) throws RequestException {
            String sql = "INSERT INTO empires (name, cash, home_star_id, user_email) VALUES (?, ?, ?, ?)";
            try (SqlStmt stmt = prepare(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, empire.getDisplayName());
                stmt.setDouble(2, 500.0);
                stmt.setInt(3, ((Star) empire.getHomeStar()).getID());
                stmt.setString(4, empire.getEmailAddr());
                stmt.update();
                empire.setID(stmt.getAutoGeneratedID());
            } catch(Exception e) {
                throw new RequestException(500, e);
            }
        }

        public List<Empire> getEmpires(int[] ids) throws SQLException {
            String sql = "SELECT * FROM empires WHERE id IN "+buildInClause(ids);

            SqlStmt stmt = prepare(sql);
            ResultSet rs = stmt.select();

            ArrayList<Empire> empires = new ArrayList<Empire>();
            while (rs.next()) {
                empires.add(new Empire(rs));
            }
            populateEmpires(empires);
            return empires;
        }

        public Empire getEmpireByEmail(String email) throws SQLException {
            String sql = "SELECT * FROM empires WHERE user_email = ?";
            SqlStmt stmt = prepare(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.select();

            ArrayList<Empire> empires = new ArrayList<Empire>();
            if (rs.next()) {
                empires.add(new Empire(rs));
            }
            if (empires.size() == 0) {
                return null;
            }

            populateEmpires(empires);
            return empires.get(0);
        }

        public List<Empire> getEmpiresByName(String name, int limit) throws SQLException {
            String sql = "SELECT * FROM empires WHERE name LIKE ? LIMIT ?";
            SqlStmt stmt = prepare(sql);
            stmt.setString(1, "%"+name+"%"); // TODO: escape?
            stmt.setInt(2, limit);
            ResultSet rs = stmt.select();

            ArrayList<Empire> empires = new ArrayList<Empire>();
            if (rs.next()) {
                empires.add(new Empire(rs));
            }

            populateEmpires(empires);
            return empires;
        }

        private void populateEmpires(List<Empire> empires) throws SQLException {
            for (Empire empire : empires) {
                try {
                    empire.setHomeStar(new StarController().getStar(empire.getHomeStarID()));
                } catch (RequestException e) {
                    // TODO: ignore??
                }
            }
        }

        private int[] getStarsForEmpire(int empireId) throws SQLException {
            String sql = "SELECT star_id FROM fleets WHERE empire_id = ?" +
                        " UNION SELECT star_id FROM colonies WHERE empire_id = ?";
            SqlStmt stmt = prepare(sql);
            stmt.setInt(1, empireId);
            stmt.setInt(2, empireId);
            ResultSet rs = stmt.select();

            ArrayList<Integer> starIds = new ArrayList<Integer>();
            while (rs.next()) {
                starIds.add(rs.getInt(1));
            }

            int[] array = new int[starIds.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = starIds.get(i);
            }
            return array;
        }
    }
}