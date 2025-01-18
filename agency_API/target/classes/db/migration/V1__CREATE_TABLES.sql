CREATE TABLE IF NOT EXISTS coupon
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    code             VARCHAR(255) NULL,
    name             VARCHAR(255) NULL,
    discount         INT NULL,
    `description`    VARCHAR(255) NULL,
    creation_date    datetime NULL,
    expiration_date  datetime NULL,
    is_active        BIT(1) NULL,
    purchase_id      BIGINT NULL,
    travel_bundle_id BIGINT NULL,
    CONSTRAINT pk_coupon PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS image
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    image            BLOB NULL,
    travel_bundle_id BIGINT NULL,
    CONSTRAINT pk_image PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS purchase
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT NULL,
    total_price DOUBLE NULL,
    purchase_date  datetime NULL,
    payment_method VARCHAR(255) NULL,
    status         SMALLINT NULL,
    CONSTRAINT pk_purchase PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS purchase_travel_bundles
(
    purchase_id       BIGINT NOT NULL,
    travel_bundles_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS rating
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    rating           INT NULL,
    user_id          BIGINT NULL,
    travel_bundle_id BIGINT NULL,
    comment          VARCHAR(255) NULL,
    CONSTRAINT pk_rating PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS travel_bundle
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    title             VARCHAR(255) NULL,
    `description`     VARCHAR(255) NULL,
    destiny           VARCHAR(255) NULL,
    start_date        date NULL,
    end_date          date NULL,
    available_bundles INT NULL,
    total_bundles     INT NULL,
    amount_to_buy     INT NULL,
    unitary_price DOUBLE NULL,
    total_price DOUBLE NULL,
    CONSTRAINT pk_travelbundle PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NULL,
    email         VARCHAR(255) NULL,
    username      VARCHAR(255) NULL,
    password      VARCHAR(255) NULL,
    phone_number  INT NULL,
    register_date date NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE purchase_travel_bundles
    ADD CONSTRAINT uc_purchase_travel_bundles_travelbundles UNIQUE (travel_bundles_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE coupon
    ADD CONSTRAINT FK_COUPON_ON_PURCHASE FOREIGN KEY (purchase_id) REFERENCES purchase (id);

ALTER TABLE coupon
    ADD CONSTRAINT FK_COUPON_ON_TRAVEL_BUNDLE FOREIGN KEY (travel_bundle_id) REFERENCES travel_bundle (id);

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_TRAVEL_BUNDLE FOREIGN KEY (travel_bundle_id) REFERENCES travel_bundle (id);

ALTER TABLE purchase
    ADD CONSTRAINT FK_PURCHASE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_TRAVEL_BUNDLE FOREIGN KEY (travel_bundle_id) REFERENCES travel_bundle (id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE purchase_travel_bundles
    ADD CONSTRAINT fk_purtrabun_on_purchase FOREIGN KEY (purchase_id) REFERENCES purchase (id);

ALTER TABLE purchase_travel_bundles
    ADD CONSTRAINT fk_purtrabun_on_travel_bundle FOREIGN KEY (travel_bundles_id) REFERENCES travel_bundle (id);