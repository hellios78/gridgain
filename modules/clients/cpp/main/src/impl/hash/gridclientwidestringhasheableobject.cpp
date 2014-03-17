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

#include "gridgain/impl/utils/gridclientdebug.hpp"

#include "gridgain/impl/hash/gridclientwidestringhasheableobject.hpp"

GridWideStringHasheableObject::GridWideStringHasheableObject(const std::wstring& s) {
    str = s;
}

int32_t GridWideStringHasheableObject::hashCode() const {
    int len = str.length();
    int32_t hash = 0;
    const wchar_t* val = str.c_str();

    for (int i = 0; i < len; i++)
        hash = 31 * hash + val[i];

    return hash;
}

void GridWideStringHasheableObject::convertToBytes(std::vector<int8_t>& bytes) const {
    bytes.clear();

    for (size_t i = 0; i< str.length(); i++) {
        char c[8];
        int len = ::wctomb(c, str[i]);

        for (int j = 0; j < len; j++)
            bytes.push_back((int8_t)c[j]);
    }
}
