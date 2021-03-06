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

package org.gridgain.grid.util;

import org.gridgain.grid.lang.*;
import org.gridgain.grid.util.typedef.*;
import org.gridgain.grid.util.typedef.internal.*;

import java.io.*;
import java.util.*;

/**
 * Finds configuration files located in {@code GRIDGAIN_HOME} folder
 * and its subfolders.
 */
public final class GridConfigurationFinder {
    /** Path to default configuration file. */
    private static final String DFLT_CFG = "config" + File.separator + "default-config.xml";

    /** Prefix for questionable paths. */
    public static final String Q_PREFIX = "(?)";

    /** */
    private static final int Q_PREFIX_LEN = Q_PREFIX.length();

    /**
     * Ensure singleton.
     */
    private GridConfigurationFinder() {
        // no-op
    }

    /**
     * Lists paths to all GridGain configuration files located in GRIDGAIN_HOME with their
     * last modification timestamps.
     *
     * @return Collection of configuration files and their last modification timestamps.
     * @throws IOException Thrown in case of any IO error.
     */
    public static List<GridBiTuple<String, Long>> getConfigFiles() throws IOException {
        return getConfigFiles(new File(U.getGridGainHome()));
    }

    /**
     * Lists paths to all GridGain configuration files located in given directory with their
     * last modification timestamps.
     *
     * @param dir Directory.
     * @return Collection of configuration files and their last modification timestamps.
     * @throws IOException Thrown in case of any IO error.
     */
    public static List<GridBiTuple<String, Long>> getConfigFiles(File dir) throws IOException {
        assert dir != null;

        LinkedList<GridBiTuple<String, Long>> lst = listFiles(dir);

        // Sort.
        Collections.sort(lst, new Comparator<GridBiTuple<String, Long>>() {
            @Override public int compare(GridBiTuple<String, Long> t1, GridBiTuple<String, Long> t2) {
                String s1 = t1.get1();
                String s2 = t2.get1();


                String q1 = s1.startsWith(Q_PREFIX) ? s1.substring(Q_PREFIX_LEN + 1) : s1;
                String q2 = s2.startsWith(Q_PREFIX) ? s2.substring(Q_PREFIX_LEN + 1) : s2;

                return q1.compareTo(q2);
            }
        });

        File dflt = new File(U.getGridGainHome() + File.separator + DFLT_CFG);

        if (dflt.exists())
            lst.addFirst(F.t(DFLT_CFG, dflt.lastModified()));

        return lst;
    }

    /**
     * Lists paths to all GridGain configuration files located in given directory with their
     * last modification timestamps.
     *
     * NOTE: default configuration path will be skipped.
     *
     * @param dir Directory.
     * @return Collection of configuration files and their last modification timestamps.
     * @throws IOException Thrown in case of any IO error.
     */
    private static LinkedList<GridBiTuple<String, Long>> listFiles(File dir) throws IOException {
        assert dir != null;

        LinkedList<GridBiTuple<String, Long>> paths = new LinkedList<>();

        for (String name : dir.list()) {
            File file = new File(dir, name);

            if (file.isDirectory())
                paths.addAll(listFiles(file));
            else if (file.getName().endsWith(".xml")) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    boolean springCfg = false;
                    boolean ggCfg = false;

                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (line.contains("http://www.springframework.org/schema/beans"))
                            springCfg = true;

                        if (line.contains("class=\"org.gridgain.grid.GridConfiguration\""))
                            ggCfg = true;

                        if (springCfg && ggCfg)
                            break;
                    }

                    if (springCfg) {
                        String path = file.getAbsolutePath().substring(U.getGridGainHome().length());

                        if (path.startsWith(File.separator))
                            path = path.substring(File.separator.length());

                        if (!path.equals(DFLT_CFG)) {
                            if (!ggCfg)
                                path = Q_PREFIX + ' ' + path;

                            paths.add(F.t(path, file.lastModified()));
                        }
                    }
                }
            }
        }

        return paths;
    }
}
