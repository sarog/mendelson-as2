#
# Update script db version 42 to db version 43
# $Author: heller $
# $Revision: 1.2 $
DELETE FROM modulelock
ALTER TABLE modulelock ADD COLUMN clientpid VARCHAR(255) NOT NULL


