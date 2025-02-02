
-- Ensure that the imageData column is properly converted from BLOB to TEXT
ALTER TABLE image MODIFY COLUMN image_data TEXT;