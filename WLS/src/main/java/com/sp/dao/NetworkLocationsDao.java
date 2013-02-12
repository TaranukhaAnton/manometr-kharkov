package com.sp.dao;

import com.sp.dto.flex.LocationNVDto;
import com.sp.model.NetworkLocation;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Paul
 * Date: 21.01.13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class NetworkLocationsDao extends BaseDao
{
    public LocationNVDto getLocationById(Integer id)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select nv.`id` id,nv.`code` `code`,\n");
        sql.append("nv.`poi_id` poiId,");
        sql.append("nv.`aoi_id` aoiId,");
        sql.append("(case when nv.`poi_id` is not null then p.descr else a.`descr` end) `name`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.lat else NULL end) `lat`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.lon else NULL end) `lon`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.radius else NULL end) `radius`,\n");
        sql.append("nv.`type` `Type`,u1.`user_name` `createdBy`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.`timestamp` else a.`timestamp` end) `createdOn`,\n");
        sql.append("u2.`user_name` `modifiedBy`,\n");
        sql.append("nv.`timestamp` `modifiedOn`,\n");
        sql.append("nv.`status` `status`\n");
        sql.append(" from network_locations nv left join\n");
        sql.append("poi p on nv.`poi_id`=p.`id` left join\n");
        sql.append("aoi a on nv.`aoi_id`=a.`id` left join\n");
        sql.append("`security_user` u1 on nv.`creator_id`=u1.`id` left join\n");
        sql.append("`security_user` u2 on nv.`modifier_id`=u2.`id`");
        sql.append(" where nv.`id`="+id);

        Session session=getSession();
        Query query=session.createSQLQuery(sql.toString())
                .addScalar("id", Hibernate.INTEGER)
                .addScalar("code", Hibernate.STRING)
                .addScalar("name", Hibernate.STRING)
                .addScalar("lat", Hibernate.DOUBLE)
                .addScalar("lon", Hibernate.DOUBLE)
                .addScalar("radius", Hibernate.INTEGER)
                .addScalar("type", Hibernate.STRING)
                .addScalar("createdBy", Hibernate.STRING)
                .addScalar("createdOn", Hibernate.TIMESTAMP)
                .addScalar("modifiedBy", Hibernate.STRING)
                .addScalar("modifiedOn", Hibernate.TIMESTAMP)
                .addScalar("status", Hibernate.STRING)
                .addScalar("poiId", Hibernate.INTEGER)
                .addScalar("aoiId", Hibernate.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(LocationNVDto.class));
        return (LocationNVDto) query.list().get(0);
    }

    public NetworkLocation getNetworkLocationById(Integer id)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from network_locations nv");
        sql.append(" where nv.`id`="+id);


        Query query=getSession().createSQLQuery(sql.toString()).addEntity(NetworkLocation.class);
        return (NetworkLocation) query.list().get(0);
    }

    public void saveNetworkLocation(LocationNVDto loc)
    {
        String sql="insert into network_locations (aoi_id, code, creator_id, modifier_id, poi_id, status, timestamp, type) values " +
                "("+((loc.getAoiId()==0)?null:loc.getAoiId())+",'"+loc.getCode()+"',133,133,+"+((loc.getPoiId()==0)?null:loc.getPoiId())+",'"+loc.getStatus()+"',CURRENT_TIMESTAMP,'"+loc.getType()+"')";
        Session session=getSession();
        Query query=session.createSQLQuery(sql);
        query.executeUpdate();
    }

    public List<LocationNVDto> getNetworkLocations()
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select nv.`id` id,nv.`code` `code`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.descr else a.`descr` end) `name`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.lat else NULL end) `lat`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.lon else NULL end) `lon`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.radius else NULL end) `radius`,\n");
        sql.append("nv.`type` `Type`,u1.`user_name` `createdBy`,\n");
        sql.append("(case when nv.`poi_id` is not null then p.`timestamp` else a.`timestamp` end) `createdOn`,\n");
        sql.append("u2.`user_name` `modifiedBy`,\n");
        sql.append("nv.`timestamp` `modifiedOn`,\n");
        sql.append("nv.`status` `status`\n");
        sql.append(" from network_locations nv left join\n");
        sql.append("poi p on nv.`poi_id`=p.`id` left join\n");
        sql.append("aoi a on nv.`aoi_id`=a.`id` left join\n");
        sql.append("`security_user` u1 on nv.`creator_id`=u1.`id` left join\n");
        sql.append("`security_user` u2 on nv.`modifier_id`=u2.`id`");

        Session session=getSession();
        Query query=session.createSQLQuery(sql.toString())
                .addScalar("id", Hibernate.INTEGER)
                .addScalar("code", Hibernate.STRING)
                .addScalar("name", Hibernate.STRING)
                .addScalar("lat", Hibernate.DOUBLE)
                .addScalar("lon", Hibernate.DOUBLE)
                .addScalar("radius", Hibernate.INTEGER)
                .addScalar("type", Hibernate.STRING)
                .addScalar("createdBy", Hibernate.STRING)
                .addScalar("createdOn", Hibernate.TIMESTAMP)
                .addScalar("modifiedBy", Hibernate.STRING)
                .addScalar("modifiedOn", Hibernate.TIMESTAMP)
                .addScalar("status", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(LocationNVDto.class));
        return query.list();
    }
}