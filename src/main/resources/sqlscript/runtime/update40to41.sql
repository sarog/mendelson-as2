#
# Update script db version 40 to db version 41
# $Author: heller $
# $Revision: 1.2 $
#
# mySQL compatibility
#
ALTER TABLE messages ALTER COLUMN compression RENAME TO msgcompression
ALTER TABLE messages ALTER COLUMN subject RENAME TO msgsubject
ALTER TABLE serverstatistic ALTER COLUMN server RENAME TO serverid
ALTER TABLE serverstatistic ALTER COLUMN compression RENAME TO msgcompression


