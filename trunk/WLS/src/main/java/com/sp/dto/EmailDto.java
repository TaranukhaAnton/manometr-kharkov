package com.sp.dto;

/**
 * Class for transportation of Email properties.
 */
public class EmailDto {

    private String host;

    private String subject;

    private String fromAddress;

    private String smtpUsername;

    private String smtpPassword;

    private Integer smtpPort;

    /**
     * Used in case when SMTP properties located on Reseller page.
     * @param host
     * @param subject
     * @param fromAddress
     * @param smtpUsername
     * @param smtpPassword
     * @param smtpPort
     */
    public EmailDto(String host, String subject, String fromAddress,
                    String smtpUsername, String smtpPassword, Integer smtpPort) {
        this.host = host;
        this.subject = subject;
        this.fromAddress = fromAddress;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
        this.smtpPort = smtpPort;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }
}
