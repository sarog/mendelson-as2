#
# Update script db version 41 to db version 42
# Add a notification flood control
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE notification add COLUMN maxnotificationspermin INTEGER DEFAULT 2 NOT NULL