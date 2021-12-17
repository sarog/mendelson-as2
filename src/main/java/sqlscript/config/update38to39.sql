#
# Update script db version 38 to db version 39
# This is for SQL compatibility
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE httpheader ALTER COLUMN key RENAME TO headerkey
ALTER TABLE httpheader ALTER COLUMN value RENAME TO headervalue
ALTER TABLE partner ALTER COLUMN name RENAME TO partnername
ALTER TABLE partner ALTER COLUMN subject RENAME TO msgsubject
ALTER TABLE partner ALTER COLUMN compression RENAME TO msgcompression
ALTER TABLE partnersystem ALTER COLUMN compression RENAME TO msgcompression