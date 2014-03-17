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

package org.gridgain.grid.spi;

import java.lang.annotation.*;

/**
 * Annotation for SPI main interface implementation. Every SPI implementation should be
 * annotated with this annotations. Kernel will not load SPI implementation without this
 * annotation.
 */
@SuppressWarnings({"JavaDoc"})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface GridSpiInfo {
    /**
     * SPI provider's author.
     */
    public String author();

    /**
     * Vendor's URL.
     */
    public String url();

    /**
     * Vendor's email (info or support).
     */
    public String email();

    /**
     * SPI implementation version.
     */
    public String version();
}
