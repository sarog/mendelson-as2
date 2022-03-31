#
# Update script db version 45 to db version 46
# Add the possibility to inform the user at postprocessing problems
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE notification ADD COLUMN notifypostprocessing INTEGER DEFAULT 0 NOT NULL
