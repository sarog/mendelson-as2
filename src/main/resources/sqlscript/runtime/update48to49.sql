#
# Update script db version 48 to db version 49
# This modified HA infrastructure in the database
# $Author: heller $
# $Revision: 1.2 $
#
#
DELETE FROM highavail
ALTER TABLE highavail DROP COLUMN ip
ALTER TABLE highavail ADD COLUMN localip VARCHAR(45) NOT NULL
ALTER TABLE highavail ADD COLUMN publicip VARCHAR(45)
ALTER TABLE highavail ADD COLUMN cloudinstanceid VARCHAR(65)


