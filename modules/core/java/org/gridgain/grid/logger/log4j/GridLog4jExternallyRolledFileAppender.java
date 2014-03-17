/* 
 Copyright (C) GridGain Systems. All Rights Reserved.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/*  _________        _____ __________________        _____
 *  __  ____/___________(_)______  /__  ____/______ ____(_)_______
 *  _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
 *  / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
 *  \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
 */

package org.gridgain.grid.logger.log4j;

import org.apache.log4j.varia.*;
import org.gridgain.grid.util.typedef.internal.*;

import java.io.*;
import java.util.*;

/**
 * Log4J {@link ExternallyRolledFileAppender} with added support for grid node IDs.
 */
public class GridLog4jExternallyRolledFileAppender extends ExternallyRolledFileAppender
    implements GridLog4jNodeIdSupported {
    /** Node ID. */
    private UUID nodeId;

    /** Basic log file name. */
    private String baseFileName;

    /**
     * Default constructor (does not do anything).
     */
    public GridLog4jExternallyRolledFileAppender() {
        init();
    }

    /**
     *
     */
    private void init() {
        GridLog4jLogger.addAppender(this);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("NonPrivateFieldAccessedInSynchronizedContext")
    @Override public synchronized void setNodeId(UUID nodeId) {
        A.notNull(nodeId, "nodeId");
        A.notNull(fileName, "fileName");

        this.nodeId = nodeId;

        if (baseFileName == null)
            baseFileName = fileName;

        fileName = U.nodeIdLogFileName(nodeId, baseFileName);
    }

    /** {@inheritDoc} */
    @Override public synchronized UUID getNodeId() {
        return nodeId;
    }

    /** {@inheritDoc} */
    @Override public synchronized void setFile(String fileName, boolean fileAppend, boolean bufIO, int bufSize)
        throws IOException {
        if (nodeId != null)
            super.setFile(fileName, fileAppend, bufIO, bufSize);
    }
}
