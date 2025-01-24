ALTER TABLE travel_bundle
    DROP COLUMN total_bundles,
    DROP COLUMN total_price;

ALTER TABLE purchase_travel_bundles
    DROP FOREIGN KEY fk_purtrabun_on_purchase;

ALTER TABLE purchase_travel_bundles
    DROP FOREIGN KEY fk_purtrabun_on_travel_bundle;

DROP TABLE IF EXISTS purchase_travel_bundles;

CREATE TABLE details_purchase (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    quantity INT NOT NULL,
    total_price DOUBLE NOT NULL,    
    travel_bundles_id BIGINT NOT NULL,
    purchase_id BIGINT NOT NULL,
    coupon_id BIGINT NULL UNIQUE,
    FOREIGN KEY (travel_bundles_id) REFERENCES travel_bundle(id),
    FOREIGN KEY (purchase_id) REFERENCES purchase(id),
    FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);


ALTER TABLE travel_bundle
    DROP FOREIGN KEY fk_coupon_id;

ALTER TABLE travel_bundle
    DROP COLUMN coupon_id;





