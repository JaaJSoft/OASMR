/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.lib.crypto;

import javax.crypto.spec.DHParameterSpec;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;

public class KeyInit implements Serializable {

    private PublicKey DHPublicKey;
    private BigInteger p;
    private BigInteger g;
    private int l;

    public KeyInit(DHParameterSpec DHSpec, PublicKey DHPublicKey) {
        this.DHPublicKey = DHPublicKey;
        p = DHSpec.getP();
        g = DHSpec.getG();
        l = DHSpec.getL();
    }

    public KeyInit(PublicKey DHPublicKey) {
        this.DHPublicKey = DHPublicKey;
    }

    public DHParameterSpec getDHSpec() {
        return new DHParameterSpec(p, g, l);
    }

    public PublicKey getDHPublicKey() {
        return DHPublicKey;
    }
}
