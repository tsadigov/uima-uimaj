/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.examples.opennlp;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.impl.JCasImpl;

/**
 * Interjection. Corresponds approximately to the part-of-speech tag UH. Updated by JCasGen Fri Dec
 * 02 14:22:24 EST 2005
 * 
 * @generated
 */
public class INTJ_Type extends Phrase_Type {
  /** @generated */
  protected FSGenerator getFSGenerator() {
    return fsGenerator;
  }

  /** @generated */
  private final FSGenerator fsGenerator = new FSGenerator() {
    public FeatureStructure createFS(int addr, CASImpl cas) {
      if (INTJ_Type.this.useExistingInstance) {
        // Return eq fs instance if already created
        FeatureStructure fs = INTJ_Type.this.jcas.getJfsFromCaddr(addr);
        if (null == fs) {
          fs = new INTJ(addr, INTJ_Type.this);
          INTJ_Type.this.jcas.putJfsFromCaddr(addr, fs);
          return fs;
        }
        return fs;
      } else
        return new INTJ(addr, INTJ_Type.this);
    }
  };

  /** @generated */
  public final static int typeIndexID = INTJ.typeIndexID;

  /**
   * @generated
   * @modifiable
   */
  public final static boolean featOkTst = JCasImpl
          .getFeatOkTst("org.apache.uima.examples.opennlp.INTJ");

  /**
   * initialize variables to correspond with Cas Type and Features
   * 
   * @generated
   */
  public INTJ_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl) this.casType, getFSGenerator());

  }
}
