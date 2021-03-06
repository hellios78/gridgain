::
:: Copyright (C) GridGain Systems. All Rights Reserved.
::
:: Licensed under the Apache License, Version 2.0 (the "License");
:: you may not use this file except in compliance with the License.
:: You may obtain a copy of the License at

::    http://www.apache.org/licenses/LICENSE-2.0
:: 
:: Unless required by applicable law or agreed to in writing, software
:: distributed under the License is distributed on an "AS IS" BASIS,
:: WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
:: See the License for the specific language governing permissions and
:: limitations under the License.

:: _________        _____ __________________        _____
:: __  ____/___________(_)______  /__  ____/______ ____(_)_______
:: _  / __  __  ___/__  / _  __  / _  / __  _  __ `/__  / __  __ \
:: / /_/ /  _  /    _  /  / /_/ /  / /_/ /  / /_/ / _  /  _  / / /
:: \____/   /_/     /_/   \_,__/   \____/   \__,_/  /_/   /_/ /_/
::
:: Version: @bat.file.version
::

::
:: Exports GRIDGAIN_LIBS variable containing classpath for GridGain.
:: Expects GRIDGAIN_HOME to be set.
:: Can be used like:
::      call %GRIDGAIN_HOME%\bin\include\setenv.bat
:: in other scripts to set classpath using exported GRIDGAIN_LIBS variable.
::

@echo off

:: USER_LIBS variable can optionally contain user's JARs/libs.
:: set USER_LIBS=

::
:: Check GRIDGAIN_HOME.
::
if defined GRIDGAIN_HOME goto run
    echo %0, ERROR: GRIDGAIN_HOME environment variable is not found.
goto :eof

:run
:: The following libraries are required for GridGain.
set GRIDGAIN_LIBS=%USER_LIBS%;%GRIDGAIN_LIBS%;%GRIDGAIN_HOME%\config\userversion;%GRIDGAIN_HOME%\libs\*

:: Uncomment if using JBoss.
:: JBOSS_HOME must point to JBoss installation folder.
:: set JBOSS_HOME=

:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\lib\jboss-common.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\lib\jboss-jmx.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\lib\jboss-system.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jbossha.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jboss-j2ee.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jboss.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jboss-transaction.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jmx-adaptor-plugin.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\server\all\lib\jnpserver.jar

:: If using JBoss AOP following libraries need to be downloaded separately
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\lib\jboss-aop-jdk50.jar
:: set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%JBOSS_HOME%\lib\jboss-aspect-library-jdk50.jar

:: Set user external libraries
set GRIDGAIN_LIBS=%GRIDGAIN_LIBS%;%GRIDGAIN_HOME%\libs\ext\*
