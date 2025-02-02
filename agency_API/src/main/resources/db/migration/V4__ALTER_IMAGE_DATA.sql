
-- Ensure that the imageData column is properly converted from BLOB to TEXT
ALTER TABLE Image MODIFY COLUMN image_data TEXT;