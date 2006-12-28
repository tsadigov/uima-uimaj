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

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.impl.JCasImpl;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Updated by JCasGen Fri Dec 02 14:22:23 EST 2005 XML source:
 * c:/workspace/uimaj-examples/opennlp/src/org/apache/uima/examples/opennlp/annotator/OpenNLPExampleTypes.xml
 * 
 * @generated
 */
public class Token extends Annotation {
  /**
   * @generated
   * @ordered
   */
  public final static int typeIndexID = JCasImpl.getNextIndex();

  /**
   * @generated
   * @ordered
   */
  public final static int type = typeIndexID;

  /** @generated */
  public int getTypeIndexID() {
    return typeIndexID;
  }

  /**
   * Never called. Disable default constructor
   * 
   * @generated
   */
  protected Token() {
  }

  /**
   * Internal - constructor used by generator
   * 
   * @generated
   */
  public Token(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }

  /** @generated */
  public Token(JCas jcas) {
    super(jcas);
    readObject();
  }

  public Token(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }

  /**
   * <!-- begin-user-doc --> Write your own initialization here <!-- end-user-doc -->
   * 
   * @generated modifiable
   */
  private void readObject() {
  }

  // *--------------*
  // * Feature: posTag

  /**
   * getter for posTag - gets
   * 
   * @generated
   */
  public String getPosTag() {
    if (Token_Type.featOkTst && ((Token_Type) jcasType).casFeat_posTag == null)
      JCasImpl.throwFeatMissing("posTag", "org.apache.uima.examples.opennlp.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type) jcasType).casFeatCode_posTag);
  }

  /**
   * setter for posTag - sets
   * 
   * @generated
   */
  public void setPosTag(String v) {
    if (Token_Type.featOkTst && ((Token_Type) jcasType).casFeat_posTag == null)
      JCasImpl.throwFeatMissing("posTag", "org.apache.uima.examples.opennlp.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type) jcasType).casFeatCode_posTag, v);
  }

  // *--------------*
  // * Feature: componentId

  /**
   * getter for componentId - gets
   * 
   * @generated
   */
  public String getComponentId() {
    if (Token_Type.featOkTst && ((Token_Type) jcasType).casFeat_componentId == null)
      JCasImpl.throwFeatMissing("componentId", "org.apache.uima.examples.opennlp.Token");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Token_Type) jcasType).casFeatCode_componentId);
  }

  /**
   * setter for componentId - sets
   * 
   * @generated
   */
  public void setComponentId(String v) {
    if (Token_Type.featOkTst && ((Token_Type) jcasType).casFeat_componentId == null)
      JCasImpl.throwFeatMissing("componentId", "org.apache.uima.examples.opennlp.Token");
    jcasType.ll_cas.ll_setStringValue(addr, ((Token_Type) jcasType).casFeatCode_componentId, v);
  }
}
