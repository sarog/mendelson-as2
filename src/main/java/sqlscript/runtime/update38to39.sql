#
# Update script db version 38 to db version 39
# $Author: heller $
# $Revision: 1.2 $
#
#Client module locks
#
CREATE TABLE modulelock(modulename VARCHAR(255) PRIMARY KEY,startlockmillis BIGINT NOT NULL,refreshlockmillies BIGINT NOT NULL,clientip VARCHAR(255) NOT NULL, clientid VARCHAR(255) NOT NULL,username VARCHAR(255) NOT NULL)
CREATE INDEX idx_refreshlockmillies ON modulelock(refreshlockmillies)



