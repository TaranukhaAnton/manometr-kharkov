package com.sp.dao;

import com.sp.model.FuelTransaction;
import com.sp.util.TimePeriod;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class FuelTransactionDao extends BaseDao {

  public List<FuelTransaction> findForUserId(int unitViewId, TimePeriod timePeriod, String restrictedVehicles) {
    Session session = getSession();
    Query query = session.createSQLQuery(

        "SELECT {f.*} " + " FROM fuel_transaction {f} "
            + "JOIN map_unit_view_unit muvu on f.unit_id = muvu.vehicle_id "
            + "JOIN unit u on f.unit_id = u.id "
            + "WHERE " + "date_drawn BETWEEN ? AND ? AND u.is_deleted = 0 "
            + "AND muvu.group_id = ? ORDER BY unit_id, date_drawn"
            + (restrictedVehicles.length() > 0 ? " AND f.unit_id NOT IN (" + restrictedVehicles + ")" : "")).addEntity("f", FuelTransaction.class);
    return query.setTimestamp(0, timePeriod.getStartDate()).setTimestamp(1, timePeriod.getEndDate())
        .setInteger(2, unitViewId).list();
  }

  public FuelTransaction findPreviousTransactionForVehicle(FuelTransaction transaction) {
    Session session = getSession();
    Query query = session.createSQLQuery(

        "SELECT {f.*} " + " FROM fuel_transaction {f} "
            + "JOIN map_unit_view_unit muvu on f.unit_id = muvu.vehicle_id "
            + "WHERE muvu.vehicle_id = ? AND date_drawn < ? ORDER BY date_drawn desc").addEntity("f",
        FuelTransaction.class);
    query.setInteger(0, transaction.getVehicle().getId());
    query.setTimestamp(1, transaction.getDateDrawn());
    query.setMaxResults(1);
    return (FuelTransaction) query.uniqueResult();
  }

  public Long calculateTransactionStatisticForPeriod(FuelTransaction transaction, TimePeriod timePeriod, int unitViewId) {
    Session session = getSession();
    Query query = session.createSQLQuery("SELECT count(*) " + " FROM fuel_transaction f "
        + "JOIN map_unit_view_unit muvu on f.unit_id = muvu.vehicle_id "
        + "WHERE muvu.vehicle_id = ? AND muvu.group_id = ? " +
          "AND date_drawn BETWEEN ? AND ? ORDER BY date_drawn desc");
    query.setInteger(0, transaction.getVehicle().getId());
    query.setInteger(1, unitViewId);
    query.setTimestamp(2, timePeriod.getStartDate());
    query.setTimestamp(3, timePeriod.getEndDate());

    return new Long(query.uniqueResult().toString());
  }

  public Long calculateFuelStatistic(FuelTransaction transaction) {
    Session session = getSession();
    Query query = session.createSQLQuery("SELECT sum(f.litres) " + " FROM fuel_transaction f "
        + "JOIN map_unit_view_unit muvu on f.unit_id = muvu.vehicle_id "
        + "WHERE muvu.vehicle_id = ? AND date_drawn <= ? ORDER BY date_drawn desc");
    query.setInteger(0, transaction.getVehicle().getId());
    query.setTimestamp(1, transaction.getDateDrawn());

    return new Double(query.uniqueResult().toString()).longValue();
  }

  public List<FuelTransaction> findByVehicleId(int vehicle_id, TimePeriod timePeriod) {
    Session session = getSession();
    Query query = session.createSQLQuery(

        "SELECT {f.*} " + " FROM fuel_transaction {f} " + "JOIN unit u on f.unit_id = u.id " + "WHERE "
            + "date_drawn BETWEEN ? AND ?" + " AND u.id = ?").addEntity("f", FuelTransaction.class);

    return query.setTimestamp(0, timePeriod.getStartDate()).setTimestamp(1, timePeriod.getEndDate()).setInteger(2,
        vehicle_id).list();
  }

  public List<FuelTransaction> findByVehicleId(int vehicle_id) {
    Session session = getSession();
    Query query = session.createSQLQuery(

        "SELECT {f.*} " + " FROM fuel_transaction {f} " + "JOIN unit u on f.unit_id = u.id " + "WHERE "
            + "u.id = ? ORDER BY date_drawn").addEntity("f", FuelTransaction.class);

    return query.setInteger(0, vehicle_id).list();

  }

}
