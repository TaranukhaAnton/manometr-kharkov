
    create table Contact (
        id bigint not null auto_increment,
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
        oldRecord_id bigint,
        profession_id bigint,
        primary key (id)
    );

    create table ContactBr (
        id bigint not null auto_increment,
        email varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        telephone varchar(255),
        primary key (id)
    );

    create table Currency (
        id bigint not null auto_increment,
        exchangeRate varchar(255),
        name varchar(255),
        primary key (id)
    );

    create table Customer (
        id bigint not null auto_increment,
        address1 varchar(255),
        address2 varchar(255),
        address3 varchar(255),
        applicationEngineering char(1) not null,
        applicationProcess char(1) not null,
        applicationProduction char(1) not null,
        applicationProject char(1) not null,
        area bigint,
        branch varchar(255),
        city bigint,
        codeOKPO varchar(255),
        country bigint,
        dateLastCorrrection datetime,
        dateOfRecord datetime,
        localityType bigint,
        mergeData datetime,
        moreInformation varchar(255),
        name varchar(255),
        new char(1) not null,
        nomList integer,
        prospect varchar(255),
        purposeForItself char(1) not null,
        purposeInstalation char(1) not null,
        purposeIntermediary char(1) not null,
        purposeSuply char(1) not null,
        questionnaire varchar(255),
        region bigint,
        registrationNumber varchar(255),
        requisite varchar(255),
        shortName varchar(255),
        site varchar(255),
        stateProperty varchar(255),
        status char(1) not null,
        headCustomer_id bigint,
        oldRecord_id bigint,
        orgForm_id bigint,
        person_id bigint,
        primary key (id)
    );

    create table OrgForm (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Profession (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    );

    create table Supplier (
        id bigint not null auto_increment,
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
        currency_id bigint,
        primary key (id)
    );

    create table User (
        id bigint not null auto_increment,
        dischargingDate datetime,
        lastName varchar(255),
        login varchar(255),
        name varchar(255),
        pass varchar(255),
        patronymic varchar(255),
        position varchar(255),
        powersLivel varchar(255),
        receptionoOnWorkDate datetime,
        tel varchar(255),
        telMob varchar(255),
        primary key (id)
    );

    alter table Contact 
        add index FK9BEFBC0032B5D3D3 (profession_id), 
        add constraint FK9BEFBC0032B5D3D3 
        foreign key (profession_id) 
        references Profession (id);

    alter table Contact 
        add index FK9BEFBC003CC144A9 (oldRecord_id), 
        add constraint FK9BEFBC003CC144A9 
        foreign key (oldRecord_id) 
        references Contact (id);

    alter table Customer 
        add index FK27FBE3FE83521FE1 (orgForm_id), 
        add constraint FK27FBE3FE83521FE1 
        foreign key (orgForm_id) 
        references OrgForm (id);

    alter table Customer 
        add index FK27FBE3FE377FA8F3 (headCustomer_id), 
        add constraint FK27FBE3FE377FA8F3 
        foreign key (headCustomer_id) 
        references Customer (id);

    alter table Customer 
        add index FK27FBE3FEA684069 (person_id), 
        add constraint FK27FBE3FEA684069 
        foreign key (person_id) 
        references User (id);

    alter table Customer 
        add index FK27FBE3FE70DC0B39 (oldRecord_id), 
        add constraint FK27FBE3FE70DC0B39 
        foreign key (oldRecord_id) 
        references Customer (id);

    alter table Supplier 
        add index FKA0B65DECC39311F3 (currency_id), 
        add constraint FKA0B65DECC39311F3 
        foreign key (currency_id) 
        references Currency (id);
