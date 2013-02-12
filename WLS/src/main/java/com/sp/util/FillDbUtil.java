package com.sp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexander
 */
public class FillDbUtil {
    //account descr => id
    private static final Map<String,Integer> createdAccountMap = new HashMap<String, Integer>();
    private static final Map<String,Integer> createdGroupMap = new HashMap<String,Integer>();
    private static final Map<String,Integer> createdLicenseMap = new HashMap<String,Integer>();

    private static final int IMEI_INDEX = 1;
    private static final int ACCOUNT_GROUP_DESCR_INDEX = 2;
    private static final int SIM_SERIAL_INDEX = 3;
    private static final int SIM_NUMBER_INDEX = 4;
    private static final int VEH_REG_INDEX = 5;

    private static final int START_PARSE_INDEX = 2;

    static{
        createdAccountMap.put("gloucestershirehospitalsnhsfou",469);
        createdGroupMap.put("gloucestershirehospitalsnhsfou",714);
        createdLicenseMap.put("gloucestershirehospitalsnhsfou",445);
    }

    public static void main(String[] args) throws IOException {
       int curAccountId;
       int curGroupId;
       int curLicId;
       int curUnitId = 3324;
       int curSimId = 3172;
        
        String text ="";
        String[] rows = text.split("<>");
        for(int i=START_PARSE_INDEX; i<rows.length;i++){
            String[] items = rows[i].split(",");
            if(items.length!=6){
                System.out.println(i + " WRONG RECORD: " + rows[i]);
                continue;
            }
            String accountGroupDescr = items[ACCOUNT_GROUP_DESCR_INDEX].trim();
            String simSerial = items[SIM_SERIAL_INDEX].trim();
            String simNumber = items[SIM_NUMBER_INDEX].trim();
            String imei = items[IMEI_INDEX].trim();
            String vehReg = items[VEH_REG_INDEX].trim();
            if(!createdAccountMap.containsKey(accountGroupDescr)){
                curAccountId = getMaxIdByMap(createdAccountMap)+1;
                curGroupId = getMaxIdByMap(createdGroupMap)+1;
                curLicId = getMaxIdByMap(createdLicenseMap)+1;
                createAccount(curAccountId,curGroupId,curLicId,accountGroupDescr);
            }else{
                curAccountId = createdAccountMap.get(accountGroupDescr);
                curGroupId = createdGroupMap.get(accountGroupDescr);
                curLicId = createdLicenseMap.get(accountGroupDescr);
            }
            curUnitId++;
            curSimId++;
            createUnit(curUnitId,vehReg,curAccountId,curGroupId,curSimId,simNumber,simSerial,imei,curLicId);
        }
   }
    

   private static int getMaxIdByMap(Map<String,Integer> map){
       int maxId = 0;
       for(Integer id : map.values()){
           if(id>maxId){
               maxId = id;
           }
       }
       return maxId;
   }

   private static void createAccount(int accountId, int groupId, int licenseId, String accGroupDescr){
       String accountSql = "INSERT INTO `account` (`id`, `descr`, `start_date`, `notes`, `primary_email`, `secondary_email`, `reseller_id`, `is_deleted`, `currency_id`, `is_set_account_image`, `account_logo_suspended`, `timestamp`, `short_journeys_default_duration`, `short_journeys_default_distance`, `disable_sticky_roads`) VALUES \n" +
               " (<account_id>, '<account_descr>', '2012-03-30', '', '<account_descr>', '<account_descr>', 6, False, 2, 0, 0, '2012-03-30 14:53:45', 0, 0, 0);";
       String groupSql = "INSERT INTO `unit_view` (`id`, `descr`, `parent_id`, `created_date`, `account_id`, `notes`, `image_file_name`) VALUES \n" +
               " (<group_id>, '<group_descr>', NULL, '2012-03-30 14:57:45', 469, '<group_descr>', NULL);";
       String mapUserAccount = "INSERT INTO `map_user_account` (`account_id`, `user_id`) VALUES " +
               " (<account_id>, 1);";
       String licenseSql = "INSERT INTO `license` (`id`, `issue_date`, `activation_date`, `end_date`, `max_node_count`, `sales_order_ref`, `account_id`, `is_deleted`) VALUES \n" +
               " (<license_id>, '2015-03-13', '2012-03-29', '2016-03-25', 1000, '<lic_descr>', <account_id>, False);";

       writeString(accountSql.replace("<account_id>",String.valueOf(accountId)).replaceAll("<account_descr>",accGroupDescr));
       writeString(groupSql.replace("<group_id>",String.valueOf(groupId)).replaceAll("<group_descr>",accGroupDescr));
       writeString(mapUserAccount.replace("<account_id>",String.valueOf(accountId)));
       writeString(licenseSql.replace("<license_id>",String.valueOf(licenseId)).replace("<lic_descr>","lic_"+licenseId).replace("<account_id>",String.valueOf(accountId)));
       createdAccountMap.put(accGroupDescr,accountId);
       createdGroupMap.put(accGroupDescr,groupId);
       createdLicenseMap.put(accGroupDescr,licenseId);
   }

    private static void createUnit(int unitId,String unitDescr,int accountId, int groupId, int simId,String simNumber, String simSerial,
                                   String imei, int licenseId){
        String unitSql = "INSERT INTO `unit` (`id`, `descr`, `registration_number`, `type_id`, `fleet_id`, `parent_id`, `lat`, `lon`, `renewal_date`, `cur_speed`, `vin`, `phone_model`, `cab_phone`, `status_id`, `associated_regular_driver_id`, `is_deleted`, `cur_direction`, `cur_street`, `cur_city`, `poi_descr`, `ignition_status`, `ignition_status_visibility`, `cost_per_mile`, `immobilize_status`, `today_journey_distance`, `road_lat`, `road_lon`, `distance_to_road`, `client_info`, `service_due_date`, `cur_postcode`, `cost_per_hour`, `mot_date`, `road_tax_date`, `insurance_due_date`, `notes`, `start_odo`, `start_odo_date`, `gps_odo`, `avg_mpg_input`, `monday_start_time_hours`, `monday_start_time_mins`, `tuesday_start_time_hours`, `tuesday_start_time_mins`, `wed_start_time_hours`, `wed_start_time_mins`, `thu_start_time_hours`, `thu_start_time_mins`, `fri_start_time_hours`, `fri_start_time_mins`, `sat_start_time_hours`, `sat_start_time_mins`, `sun_start_time_hours`, `sun_start_time_mins`, `monday_end_time_hours`, `monday_end_time_mins`, `tuesday_end_time_hours`, `tuesday_end_time_mins`, `wed_end_time_hours`, `wed_end_time_mins`, `thu_end_time_hours`, `thu_end_time_mins`, `fri_end_time_hours`, `fri_end_time_mins`, `sat_end_time_hours`, `sat_end_time_mins`, `sun_end_time_hours`, `sun_end_time_mins`, `odo_start_time_hours`, `odo_start_time_mins`, `monday`, `tuesday`, `wednesday`, `thursday`, `friday`, `saturday`, `sunday`, `today_journey_time`, `digital_not_ignition_input_height`, `timestamp`, `aoi_descr`, `in_stealth_mode`, `prev_not_ign_value_in_stealth_mode`, `prev_date_in_stealth_mode`, `cur_journey_start_date`, `is_accel_available`, `current_driver_id`, `reseller_id`, `is_ignition_active`, `digital_input1_high`, `digital_input1_name`, `digital_input2_high`, `digital_input2_name`, `digital_input3_high`, `digital_input3_name`, `digital_input4_high`, `digital_input4_name`, `digital_input5_high`, `digital_input5_name`, `digital_input6_high`, `digital_input6_name`, `delete_date`, `immobilization_date`, `last_renewal_tracking_device_id`, `is_journey_finished`) VALUES \n" +
                       "  (<unit_id>, '<unit_descr>', '<unit_descr>', 112, '1', NULL, 52.9894830508474, -2.1342345679012, '2012-03-30 15:19:08', 0, '', '', '', 1, NULL, False, 0, NULL, NULL, NULL, 'off', 0, 0, False, 0, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, '', 0, NULL, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2012-03-30 15:19:08', NULL, 0, NULL, NULL, NULL, 0, NULL, 6, 0, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, 0);";
        String mapUnitAccountSql = "INSERT INTO `map_unit_account` (`account_id`, `unit_id`) VALUES \n" +
               " (<account_id>, <unit_id>);";
        String mapUnitGroupSql = "INSERT INTO `map_unit_view_unit` (`group_id`, `vehicle_id`) VALUES \n" +
               " (<group_id>, <unit_id>);";
        String simSql = "INSERT INTO `sim` (`id`, `number`, `start_date`, `end_date`, `owner_id`, `serial_num`, `contract_operator_id`, `is_deleted`, `account_id`, `reseller_id`, `timestamp`) VALUES \n" +
                      "  (<sim_id>, '<sim_number>', '2012-03-30', '2012-03-30', NULL, '<sim_serial>', 14, False, <account_id>, 6, '2012-03-30 15:49:34');";
        String imeiSql = "INSERT INTO `imei` (`box_type_id`, `imei`, `version`, `unit_id`, `license_id`, `account_id`, `notes`, `assigned_sim_id`, `owner_id`, `is_deleted`, `is_immobilize`, `gts_response_date`, `gts_response_text`, `device_response_date`, `device_response_text`, `io_status_response_date`, `io_status_response_text`, `info_response_date`, `info_response_text`, `last_config_datetime_response_date`, `last_config_datetime_response_text`, `version_response_date`, `version_response_text`, `time_based_acquisition_response_date`, `time_based_acquisition_response_text`, `digital_input1_enable`, `digital_input1_name`, `digital_input1_verb`, `digital_input2_enable`, `digital_input2_name`, `digital_input2_verb`, `digital_input3_enable`, `digital_input3_name`, `digital_input3_verb`, `digital_input4_enable`, `digital_input4_name`, `digital_input4_verb`, `digital_input5_enable`, `digital_input5_name`, `digital_input5_verb`, `digital_input6_enable`, `digital_input6_name`, `digital_input6_verb`, `is_ignition_input_1`, `digital_input_name`, `digital_value_height`, `digital_value_low`, `is_can_enabled`, `digital_heigh_color`, `digital_low_color`, `digital_input3_value_high`, `digital_input3_value_low`, `digital_input4_value_high`, `digital_input4_value_low`, `digital_input5_value_high`, `digital_input5_value_low`, `digital_input6_value_high`, `digital_input6_value_low`, `timestamp`, `is_replication_enabled`, `is_stealth_mode`, `digital_input2_value_high`, `digital_input2_value_low`, `digital_input1_value_high`, `digital_input1_value_low`, `left_panel_display_din_number`, `is_retain_journey_end`, `is_calculated_orientation`, `reseller_id`, `ignition_input_number`, `stealth_mode_input_number`, `digital_input1_high_color`, `digital_input1_low_color`, `digital_input2_high_color`, `digital_input2_low_color`, `digital_input3_high_color`, `digital_input3_low_color`, `digital_input4_high_color`, `digital_input4_low_color`, `is_daylight_saving`, `last_collector`, `is_always_display_driver_name`, `is_max_acceleration_force_allowed`, `is_max_braking_force_allowed`, `is_max_cornering_allowed`, `is_max_allowed_speed_allowed`, `max_acceleration_force_actual_val`, `max_braking_force_actual_val`, `max_cornering_actual_val`, `max_allowed_speed_actual_val`, `max_acceleration_force_orange_val`, `max_braking_force_orange_val`, `max_cornering_orange_val`, `max_allowed_speed_orange_val`, `max_acceleration_force_red_val`, `max_braking_force_red_val`, `max_cornering_red_val`, `max_allowed_speed_red_val`, `is_speed_limit_allowed`, `is_green_driving_allowed`, `is_overspeed_allowed`) VALUES \n" +
                        "  (16, '<imei_imei>', '', <unit_id>, <license_id>, <account_id>, '', <sim_id>, NULL, False, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, '', NULL, 0, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL, 0, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2012-03-30 16:11:42', 0, 0, '', '', NULL, NULL, 0, 0, 0, 6, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);";
        writeString(unitSql.replace("<unit_id>",String.valueOf(unitId)).replaceAll("<unit_descr>",unitDescr));
        writeString(mapUnitAccountSql.replace("<account_id>",String.valueOf(accountId)).replace("<unit_id>",String.valueOf(unitId)));
        writeString(mapUnitGroupSql.replace("<group_id>",String.valueOf(groupId)).replace("<unit_id>",String.valueOf(unitId)));
        writeString(simSql.replace("<sim_id>",String.valueOf(simId)).replace("<sim_number>",simNumber).
                    replace("<sim_serial>",simSerial).replace("<account_id>",String.valueOf(accountId)));
        writeString(imeiSql.replace("<imei_imei>",imei).replace("<unit_id>",String.valueOf(unitId))
                .replace("<license_id>",String.valueOf(licenseId)).replace("<account_id>",String.valueOf(accountId))
                .replace("<sim_id>",String.valueOf(simId)));

    }

   private static void writeString(String str){
       System.out.println(str);
   }

}
