
    create table Contact (
        id int8 not null,
        EMail varchar(255),
        customer varchar(255),
        fax varchar(255),
        group1 char(1) not null,
        group2 char(1) not null,
        group3 char(1) not null,
        lastName varchar(255),
        method varchar(255),
        mobTel varchar(255),
        name varchar(255),
        patronymic varchar(255),
        purchaseRole1 char(1) not null,
        purchaseRole2 char(1) not null,
        purchaseRole3 char(1) not null,
        purchaseRole4 char(1) not null,
        purchaseRole5 char(1) not null,
        purchaseRole6 char(1) not null,
        purchaseRole7 char(1) not null,
        status char(1) not null,
        subdivision varchar(255),
        tel varchar(255),
        oldRecord_id int8,
        profession_id int8,
        primary key (id)
    );

    create table Currency (
        id int8 not null,
        exchangeRate varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table Customer (
        id int8 not null,
        address1 varchar(255),
        address2 varchar(255),
        address3 varchar(255),
        applicationEngineering char(1) not null,
        applicationProcess char(1) not null,
        applicationProduction char(1) not null,
        applicationProject char(1) not null,
        area int8,
        branch varchar(255),
        city int8,
        codeOKPO varchar(255),
        country int8,
        dateLastCorrrection timestamp,
        dateOfRecord timestamp,
        localityType int8,
        mergeData timestamp,
        moreInformation varchar(255),
        name varchar(255),
        new char(1) not null,
        nomList int4,
        prospect varchar(255),
        purposeForItself char(1) not null,
        purposeInstalation char(1) not null,
        purposeIntermediary char(1) not null,
        purposeSuply char(1) not null,
        questionnaire varchar(255),
        region int8,
        registrationNumber varchar(255),
        requisite varchar(255),
        shortName varchar(255),
        site varchar(255),
        stateProperty varchar(255),
        status char(1) not null,
        headCustomer_id int8,
        oldRecord_id int8,
        orgForm_id int8,
        person_id int8,
        primary key (id)
    );

    create table InvoiceFilter (
        id int8 not null,
        currencyFilter varchar(255),
        f0 int4,
        f1 int4,
        f1From int4,
        f1To int4,
        f2 int4,
        f2From timestamp,
        f2To timestamp,
        purposeFilter varchar(255),
        stateFilter varchar(255),
        users varchar(255),
        primary key (id)
    );

    create table OrgForm (
        id int8 not null,
        name varchar(255),
        primary key (id)
    );

    create table Profession (
        id int8 not null,
        name varchar(255),
        primary key (id)
    );

    create table Supplier (
        id int8 not null,
        BIC varchar(255),
        FIOChief varchar(255),
        NDSPayerNom varchar(255),
        address varchar(255),
        alias varchar(255),
        bank varchar(255),
        bankBeneficiaryASS varchar(255),
        bankBeneficiaryAddress varchar(255),
        bankBeneficiaryName varchar(255),
        bankBeneficiarySWIFT varchar(255),
        bankMediatorAddress varchar(255),
        bankMediatorName varchar(255),
        bankMediatorSWIFT varchar(255),
        chief varchar(255),
        erdpou varchar(255),
        inn varchar(255),
        innkpp varchar(255),
        ks varchar(255),
        logo varchar(255),
        mfoBank varchar(255),
        name varchar(255),
        ogrn varchar(255),
        okpo varchar(255),
        phone varchar(255),
        rs varchar(255),
        taxationScheme varchar(255),
        ts varchar(255),
        currency_id int8,
        primary key (id)
    );

    create table User (
        id int8 not null,
        dischargingDate timestamp,
        lastName varchar(255),
        login varchar(255),
        name varchar(255),
        pass varchar(255),
        patronymic varchar(255),
        position varchar(255),
        powersLevel varchar(255),
        receptionOnWorkDate timestamp,
        tel varchar(255),
        telMob varchar(255),
        invoiceFilter_id int8,
        primary key (id)
    );

    alter table Contact 
        add constraint FK9BEFBC0032B5D3D3 
        foreign key (profession_id) 
        references Profession;

    alter table Contact 
        add constraint FK9BEFBC003CC144A9 
        foreign key (oldRecord_id) 
        references Contact;

    alter table Customer 
        add constraint FK27FBE3FE83521FE1 
        foreign key (orgForm_id) 
        references OrgForm;

    alter table Customer 
        add constraint FK27FBE3FE377FA8F3 
        foreign key (headCustomer_id) 
        references Customer;

    alter table Customer 
        add constraint FK27FBE3FEA684069 
        foreign key (person_id) 
        references User;

    alter table Customer 
        add constraint FK27FBE3FE70DC0B39 
        foreign key (oldRecord_id) 
        references Customer;

    alter table Supplier 
        add constraint FKA0B65DECC39311F3 
        foreign key (currency_id) 
        references Currency;

    alter table User 
        add constraint FK285FEBC45E6580 
        foreign key (invoiceFilter_id) 
        references InvoiceFilter;

    create sequence hibernate_sequence;
