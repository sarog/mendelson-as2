#
# Update script db version 43 to db version 44
# This will add the event queue for event postprocessing
# $Author: heller $
# $Revision: 1.2 $
CREATE TABLE processingeventqueue(messageid VARCHAR(255) PRIMARY KEY,mdnid VARCHAR(255),eventtype INTEGER NOT NULL,initdate BIGINT NOT NULL,command VARCHAR(2048) NOT NULL);
CREATE INDEX idx_processingeventqueue_initdate ON processingeventqueue(initdate);

