CREATE TABLE IF NOT EXISTS details_purchase
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    quantity          INT    NOT NULL,
    total_price DOUBLE NULL,
    travel_bundles_id BIGINT NOT NULL,
    purchase_id       BIGINT NOT NULL,
    CONSTRAINT pk_detailspurchase PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS details_shopping_cart
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    quantity          INT    NOT NULL,
    total_price DOUBLE NULL,
    travel_bundles_id BIGINT NOT NULL,
    shopping_cart_id  BIGINT NOT NULL,
    CONSTRAINT pk_detailsshoppingcart PRIMARY KEY (id)
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
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT NULL,
    total_price DOUBLE NULL,
    purchase_date datetime NULL,
    CONSTRAINT pk_purchase PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS rating
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    rating DOUBLE NULL,
    user_id          BIGINT NULL,
    travel_bundle_id BIGINT NULL,
    comment          VARCHAR(255) NULL,
    CONSTRAINT pk_rating PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shopping_cart
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    user_id       BIGINT NULL,
    total_price DOUBLE NULL,
    purchase_date datetime NULL,
    CONSTRAINT pk_shoppingcart PRIMARY KEY (id)
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
    unitary_price DOUBLE NULL,
    user_id           BIGINT NOT NULL,
    CONSTRAINT pk_travelbundle PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255) NULL,
    last_name     VARCHAR(255) NULL,
    identity_card INT          NOT NULL,
    email         VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    phone_number  INT NULL,
    register_date date NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT uc_user_identitycard UNIQUE (identity_card);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE details_purchase
    ADD CONSTRAINT FK_DETAILSPURCHASE_ON_PURCHASE FOREIGN KEY (purchase_id) REFERENCES purchase (id);

ALTER TABLE details_purchase
    ADD CONSTRAINT FK_DETAILSPURCHASE_ON_TRAVEL_BUNDLES FOREIGN KEY (travel_bundles_id) REFERENCES travel_bundle (id);

ALTER TABLE details_shopping_cart
    ADD CONSTRAINT FK_DETAILSSHOPPINGCART_ON_SHOPPING_CART FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);

ALTER TABLE details_shopping_cart
    ADD CONSTRAINT FK_DETAILSSHOPPINGCART_ON_TRAVEL_BUNDLES FOREIGN KEY (travel_bundles_id) REFERENCES travel_bundle (id);

ALTER TABLE image
    ADD CONSTRAINT FK_IMAGE_ON_TRAVEL_BUNDLE FOREIGN KEY (travel_bundle_id) REFERENCES travel_bundle (id);

ALTER TABLE purchase
    ADD CONSTRAINT FK_PURCHASE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_TRAVEL_BUNDLE FOREIGN KEY (travel_bundle_id) REFERENCES travel_bundle (id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE shopping_cart
    ADD CONSTRAINT FK_SHOPPINGCART_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE travel_bundle
    ADD CONSTRAINT FK_TRAVELBUNDLE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);