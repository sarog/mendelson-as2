#
# Update script db version 47 to db version 48
# This creates HA infrastructure in the database
# $Author: heller $
# $Revision: 1.2 $
#
#
CREATE INDEX idx_messages_state ON messages(state);


