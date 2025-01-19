
-- Drop foreign key constraints
ALTER TABLE coupon
    DROP FOREIGN KEY FK_COUPON_ON_PURCHASE;
ALTER TABLE coupon
    DROP FOREIGN KEY FK_COUPON_ON_TRAVEL_BUNDLE;

-- Drop purchase_id and travel_bundle_id columns
ALTER TABLE coupon
    DROP COLUMN purchase_id;
ALTER TABLE coupon
    DROP COLUMN travel_bundle_id;

-- Add new coupon_id column to TravelBundle
ALTER TABLE travel_bundle
    ADD COLUMN coupon_id BIGINT NOT NULL UNIQUE ;

-- Add foreign key constraint for coupon_id
ALTER TABLE travel_bundle
    ADD CONSTRAINT fk_coupon_id
        FOREIGN KEY (coupon_id)
            REFERENCES coupon(id);


