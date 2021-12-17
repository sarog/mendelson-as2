#
# Update script db version 40 to db version 41
# Enable/disable dir polling
# $Author: heller $
# $Revision: 1.2 $
#
#
ALTER TABLE partner add COLUMN enabledirpoll INTEGER DEFAULT 1 NOT NULL