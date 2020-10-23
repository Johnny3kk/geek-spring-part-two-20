    create table products (
       id bigint not null auto_increment,
        title varchar(255) not null,
        cost decimal not null,
        primary key (id)
    ) engine=InnoDB;
GO
