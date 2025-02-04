ALTER TABLE shopping_cart DROP COLUMN purchase_date;
ALTER TABLE details_purchase DROP FOREIGN KEY FK_DETAILSPURCHASE_ON_TRAVEL_BUNDLES;
ALTER TABLE details_purchase DROP COLUMN travel_bundles_id;

ALTER TABLE details_purchase ADD COLUMN travel_bundle_title VARCHAR(255) NULL;
ALTER TABLE details_purchase ADD COLUMN unit_price DOUBLE NULL;




