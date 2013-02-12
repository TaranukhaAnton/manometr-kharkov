package com.sp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: andrew
 * Date: 02.07.2010
 */
@Entity
@Table(name = "replicated_incoming_log")
public class ReplicatedIncomingLogRecord extends LightVehicleIncomingLogRecord {
}
