#
# Update script db version 41 to db version 42
# $Author: heller $
# $Revision: 1.2 $

ALTER TABLE mdn ADD COLUMN dispositionstate VARCHAR(255)


