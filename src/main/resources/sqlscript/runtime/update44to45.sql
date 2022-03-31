#
# Update script db version 44 to db version 45
# This will modify  the event queue for event postprocessing
# $Author: heller $
# $Revision: 1.2 $
#
#ensure the table is empty
DELETE FROM processingeventqueue
ALTER TABLE processingeventqueue DROP COLUMN command
ALTER TABLE processingeventqueue ADD COLUMN parameterlist VARCHAR(2048)
ALTER TABLE processingeventqueue ADD COLUMN processtype INTEGER NOT NULL

