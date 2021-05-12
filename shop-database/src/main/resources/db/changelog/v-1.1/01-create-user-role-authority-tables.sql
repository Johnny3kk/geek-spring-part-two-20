    create table roles (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;
GO

    create table authorities (
       id bigint not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;
GO

    create table users (
       id bigint not null auto_increment,
        age integer,
        email varchar(255),
        name varchar(32) not null,
        password varchar(128) not null,
        primary key (id)
    ) engine=InnoDB;
GO

    create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    ) engine=InnoDB;
GO

    create table roles_authorities (
       role_id bigint not null,
        authority_id bigint not null,
        primary key (authority_id, role_id)
    ) engine=InnoDB;
GO

    alter table users_roles
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy
       foreign key (role_id)
       references roles (id);
GO

    alter table users_roles
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa
       foreign key (user_id)
       references users (id);
GO

    alter table roles_authorities
       add constraint fk_role_id_1
       foreign key (role_id)
       references roles (id);
GO

    alter table roles_authorities
       add constraint fk_authority_id_1
       foreign key (authority_id)
       references authorities (id);
GO
