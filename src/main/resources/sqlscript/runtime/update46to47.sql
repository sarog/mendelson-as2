#
# Update script db version 46 to db version 47
# This creates HA infrastructure in the database
# $Author: heller $
# $Revision: 1.2 $
#
#
#High Availability changes: log the instances and their online time
#
DELETE FROM highavail
ALTER TABLE highavail DROP COLUMN uniqueid
ALTER TABLE highavail ADD COLUMN uniqueid VARCHAR(8) NOT NULL
CREATE INDEX idx_highavail_starttime ON highavail(starttime)
CREATE INDEX idx_highavail_lastupdatetime ON highavail(lastupdatetime)


