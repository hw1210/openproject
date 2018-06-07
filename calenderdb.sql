create table userinfo(
	uid mediumint not null auto_increment primary key,
    uname varchar(20) default 'null',
    upnum char(20) not null unique,
    uimg varchar(10) not null default '1',
    utsize char(5) not null default '10'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table groupinfo(
	gid mediumint not null auto_increment primary key,
    gname varchar(10) default 'null'


)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table groupuser(
	gugid mediumint not null,
    guuid mediumint not null,
    guvisible smallint default 1,
    primary key(gugid,guuid),
    foreign key(gugid) references groupinfo(gid) on update cascade on delete cascade,
    foreign key(guuid) references userinfo(uid) on update cascade on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table todo(
tid mediumint not null auto_increment primary key,
tname varchar(20) default 'null',
tgid mediumint default NULL,
tuid mediumint default NULL,
tallday smallint default 0,
tsdate int(20) default NULL,
tedate int(20) default NULL,
foreign key(tgid) references groupuser(gugid) on update cascade on delete cascade,
foreign key(tuid) references userinfo(uid) on update cascade on delete cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
