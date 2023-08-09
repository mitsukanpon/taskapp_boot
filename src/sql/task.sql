create table task(
    id              INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title           TEXT(50)    NOT NULL,
    due_date        DATE        NOT NULL,
    complete_flg    INT         NOT NULL DEFAULT 0,
    username        VARCHAR(60) NOT NULL,
    INDEX par_ind (username),
    FOREIGN KEY (username) REFERENCES USER(username)
    );