create table bs_mac_address(
    mac_address char(128) NOT NULL ,
    bs_id char(128) NOT NULL ,
    primary key (bs_id)
);

drop table bs_user;

create table bs_user(
    id int AUTO_INCREMENT NOT NULL ,
    bs_id char(128) NOT NULL,
    bs_pseudo char(128) NOT NULL,
    primary key (id)
);

create table game(
    game_id int AUTO_INCREMENT NOT NULL,
    bs_id char(128) NOT NULL,
    game_date DATE NOT NULL,
    game_nb_round int NOT NULL,
    game_nb_boat_player int NOT NULL,
    game_nb_boat_bot int NOT NULL,
    game_winner char(128) NOT NULL,
    primary key (game_id)
);