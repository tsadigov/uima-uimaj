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
 
package org.apache.uima.jcas;

import java.io.InputStream;
import java.util.Iterator;
import java.util.ListIterator;

import org.apache.uima.cas.AbstractCas;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.ConstraintFactory;
import org.apache.uima.cas.FSIndexRepository;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FSMatchConstraint;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeaturePath;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.FeatureValuePath;
import org.apache.uima.cas.SofaFS;
import org.apache.uima.cas.SofaID;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.admin.CASAdminException;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.LowLevelCAS;
import org.apache.uima.cas.impl.LowLevelIndexRepository;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FloatArray;
import org.apache.uima.jcas.cas.IntegerArray;
import org.apache.uima.jcas.cas.Sofa;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.cas.TOP_Type;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Java Cover Classes based Object-oriented CAS (Common Analysis System) API.
 * 
 * <p>
 * A <code>JCas</code> object provides the starting point for working with the CAS using
 * Java Cover Classes for each type, generated by the utility JCasGen. 
 * <p>
 * This interface extends the CAS Interface, providing all the same functionality, plus
 * some specific to the JCas.
 * <p>
 * It supports the creation of new instances of CAS types, using the normal Java "new"
 * operator.  
 * <p>
 * You can create a <code>JCas</code> object from a CAS object by calling the getJCas()
 * method on the CAS object. 
 */
public interface JCas extends AbstractCas {

  /**
   * (internal use)
   */
  public final static int INVALID_FEATURE_CODE = -1;

  // *********************************
  // * Getters for read-only objects *
  // *********************************
  /** return the FSIndexRepository object for this Cas. */
  public abstract FSIndexRepository getFSIndexRepository();

  public abstract LowLevelIndexRepository getLowLevelIndexRepository();

  /** return the Cas object for this JCas instantiation */
  public abstract CAS getCas();

  /** internal use */
  public abstract CASImpl getCasImpl();

  /** internal use */
  public abstract LowLevelCAS getLowLevelCas();

  /**
   * get the JCas _Type instance for a particular CAS type constant
   * 
   * @param i
   *          the CAS type constant, written as Foo.type
   * @return the instance of the JCas xxx_Type object for the specified type
   */
  public abstract TOP_Type getType(int i);

  /**
   * Given Foo.type, return the corresponding CAS Type object. This is useful in the methods which
   * require a CAS Type, for instance iterator creation.
   * 
   * @param i -
   *          index returned by Foo.type
   * @return the CAS Java Type object for this CAS Type.
   */
  public abstract Type getCasType(int i);

  /**
   * get the JCas x_Type instance for a particular Java instance of a type
   * 
   * @param instance
   * @return the associated xxx_Type instance
   * @deprecated use instance.jcasType instead - faster
   */
  @Deprecated
  public abstract TOP_Type getType(TOP instance);

  /**
   * Internal use - looks up a type-name-string in the CAS type system and returns the Cas Type
   * object. Throws CASException if the type isn't found
   */
  public abstract Type getRequiredType(String s) throws CASException;

  /**
   * Internal use - look up a feature-name-string in the CAS type system and returns the Cas Feature
   * object. Throws CASException if the feature isn't found
   */
  public abstract Feature getRequiredFeature(Type t, String s) throws CASException;

  /**
   * Internal Use - look up a feature-name-string in the CAS type system and returns the Cas Feature
   * object. If the feature isn't found, adds an exception to the errorSet but doesn't throw
   */

  public abstract Feature getRequiredFeatureDE(Type t, String s, String rangeName, boolean featOkTst);

  /**
   * Internal Use - sets the corresponding Java instance for a Cas instance
   */
  public abstract void putJfsFromCaddr(int casAddr, FeatureStructure fs);

  /**
   * Internal Use - sets the corresponding Java instance for a Cas instance
   */
  public abstract TOP getJfsFromCaddr(int casAddr);

  /**
   * Internal Use. 
   */
  public abstract void checkArrayBounds(int fsRef, int pos);

  /**
   * Internal Use - throw missing feature exception at runtime.
   */
  public void throwFeatMissing(String feat, String type);
  
  /**
   * @deprecated As of v2.0, use {#getView(String)}. From the view you can access the Sofa data, or
   *             call {@link #getSofa()} if you truly need to access the SofaFS object.
   */
  @Deprecated
  public abstract Sofa getSofa(SofaID sofaID);

  /**
   * Get the Sofa feature structure associated with this JCas view.
   * 
   * @return The SofaFS associated with this JCas view.
   */  
  public abstract Sofa getSofa();

  /**
   * Create a view and its underlying Sofa (subject of analysis). The view provides access to the
   * Sofa data and the index repository that contains metadata (annotations and other feature
   * structures) pertaining to that Sofa.
   * <p>
   * This method creates the underlying Sofa feature structure, but does not set the Sofa data.
   * Setting ths Sofa data must be done by calling {@link #setSofaDataArray(FeatureStructure, String)},
   * {@link #setSofaDataString(String, String)} or {@link #setSofaDataURI(String, String)} on the
   * JCas view returned by this method.
   * 
   * @param localViewName
   *          the local name, before any sofa name mapping is done, for this view (note: this is the
   *          same as the associated Sofa name).
   * 
   * @return The view corresponding to this local name.
   * @throws CASRuntimeException
   *           if a View with this name already exists in this CAS
   */
  public abstract JCas createView(String sofaID) throws CASException;

  /**
   * Create a JCas view for a Sofa. 
   * 
   * @param aSofa
   *          a Sofa feature structure in this CAS.
   * 
   * @return The JCas view for the given Sofa.
   */  
  public abstract JCas getJCas(Sofa sofa) throws CASException;

  /**
   * Gets the JCas-based interface to the Index Repository. Provides the same functionality
   * as {@link #getFSIndexRepository()} except that the methods that take a "type"
   * argument take type arguments obtainable easily from the JCas type.
   *
   * @return the JCas-based interface to the index repository
   */
  public abstract JFSIndexRepository getJFSIndexRepository();

  /**
   * Gets the document annotation. The object returned from this method can be typecast to
   * {@link org.apache.uima.jcas.tcas.DocumentAnnotation}.
   * <p>
   * The reason that the return type of this method is not DocumentAnnotation is because of problems
   * that arise when using the UIMA Extension ClassLoader to load annotator classes. The
   * DocumentAnnotation type may be defined in the UIMA extension ClassLoader, differently than in
   * the framework ClassLoader.
   * 
   * @return The one instance of the DocumentAnnotation annotation.
   * @see org.apache.uima.cas.CAS#getDocumentAnnotation
   */
  public abstract TOP getDocumentAnnotationFs();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */

  public abstract StringArray getStringArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract IntegerArray getIntegerArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract FloatArray getFloatArray0L();

  /**
   * A constant for each cas which holds a 0-length instance. Since this can be a common value, we
   * avoid creating multiple copies of it. All uses can use the same valuee because it is not
   * updatable (it has no subfields). This is initialized lazily on first reference, and reset when
   * the CAS is reset.
   */
  public abstract FSArray getFSArray0L();

  /**
   * initialize the JCas for new Cas content. Not used, does nothing.
   * 
   * @deprecated not required, does nothing
   */
  @Deprecated
  public abstract void processInit();

  /**
   * Get the view for a Sofa (subject of analysis). The view provides access to the Sofa data and
   * the index repository that contains metadata (annotations and other feature structures)
   * pertaining to that Sofa.
   * 
   * @param localViewName
   *          the local name, before any sofa name mapping is done, for this view (note: this is the
   *          same as the associated Sofa name).
   * 
   * @return The view corresponding to this local name.
   * @throws CASException
   *           
   */
  JCas getView(String localViewName) throws CASException;
  
  /**
   * Get the view for a Sofa (subject of analysis). The view provides access to the Sofa data and
   * the index repository that contains metadata (annotations and other feature structures)
   * pertaining to that Sofa.
   * 
   * @param aSofa
   *          a Sofa feature structure in the CAS
   * 
   * @return The view for the given Sofa
   */
  JCas getView(SofaFS aSofa) throws CASException;

  /**
   * This part of the CAS interface is shared among CAS and JCAS interfaces
   * If you change it in one of the interfaces, consider changing it in the 
   * other
   */

  
  ///////////////////////////////////////////////////////////////////////////
  //
  //  Standard CAS Methods
  //
  ///////////////////////////////////////////////////////////////////////////
  /**
   * Return the type system of this CAS instance.
   * 
   * @return The type system, or <code>null</code> if none is available.
   * @exception CASRuntimeException
   *              If the type system has not been committed.
   */
  TypeSystem getTypeSystem() throws CASRuntimeException;
  
  /**
   * Create a Subject of Analysis. The new sofaFS is automatically added to the SofaIndex.
   * 
   * @return The sofaFS.
   * 
   * @deprecated As of v2.0, use {@link #createView(String)} instead.
   */
  @Deprecated
  SofaFS createSofa(SofaID sofaID, String mimeType);

  /**
   * Get iterator for all SofaFS in the CAS.
   * 
   * @return an iterator over SofaFS.
   */
  FSIterator<SofaFS> getSofaIterator();

  /**
   * Create an iterator over structures satisfying a given constraint. Constraints are described in
   * the javadocs for {@link ConstraintFactory} and related classes.
   * 
   * @param it
   *          The input iterator.
   * @param cons
   *          The constraint specifying what structures should be returned.
   * @return An iterator over FSs.
   */
  <T extends FeatureStructure> FSIterator<T> createFilteredIterator(FSIterator<T> it, FSMatchConstraint cons);

  /**
   * Get a constraint factory. A constraint factory is a simple way of creating
   * {@link org.apache.uima.cas.FSMatchConstraint FSMatchConstraints}.
   * 
   * @return A constraint factory to create new FS constraints.
   */
  ConstraintFactory getConstraintFactory();

  /**
   * Create a feature path. This is mainly useful for creating
   * {@link org.apache.uima.cas.FSMatchConstraint FSMatchConstraints}.
   * 
   * @return A new, empty feature path.
   */
  FeaturePath createFeaturePath();

  /**
   * Get the index repository.
   * 
   * @return The index repository, or <code>null</code> if none is available.
   */
  FSIndexRepository getIndexRepository();

  /**
   * Wrap a standard Java {@link java.util.ListIterator ListIterator} around an FSListIterator. Use
   * if you feel more comfortable with java style iterators.
   * 
   * @param it
   *          The <code>FSListIterator</code> to be wrapped.
   * @return An equivalent <code>ListIterator</code>.
   */
  <T extends FeatureStructure> ListIterator<T> fs2listIterator(FSIterator<T> it);

  /**
   * Reset the CAS, emptying it of all content. Feature structures and iterators will no longer be
   * valid. Note: this method may only be called from an application. Calling it from an annotator
   * will trigger a runtime exception.
   * 
   * @throws CASRuntimeException
   *           When called out of sequence.
   * @see org.apache.uima.cas.admin.CASMgr
   */
  void reset() throws CASAdminException;
  
  /**
   * Get the view name. The view name is the same as the name of the view's Sofa, retrieved by
   * getSofa().getSofaID(), except for the initial View before its Sofa has been created.
   * 
   * @return The name of the view
   */
  String getViewName();

  /**
   * Estimate the memory consumption of this CAS instance (in bytes).
   * 
   * @return The estimated memory used by this CAS instance.
   */
  int size();

  /**
   * Create a feature-value path from a string.
   * 
   * @param featureValuePath
   *          String representation of the feature-value path.
   * @return Feature-value path object.
   * @throws CASRuntimeException
   *           If the input string is not well-formed.
   */
  FeatureValuePath createFeatureValuePath(String featureValuePath) throws CASRuntimeException;

  /**
   * Set the document text. Once set, Sofa data is immutable, and cannot be set again until the CAS
   * has been reset.
   * 
   * @param text
   *          The text to be analyzed.
   * @exception CASRuntimeException
   *              If the Sofa data has already been set.
   */
  void setDocumentText(String text) throws CASRuntimeException;

  /**
   * Set the document text. Once set, Sofa data is immutable, and cannot be set again until the CAS
   * has been reset.
   * 
   * @param text
   *          The text to be analyzed.
   * @param mime
   *          The mime type of the data
   * @exception CASRuntimeException
   *              If the Sofa data has already been set.
   */
  void setSofaDataString(String text, String mimetype) throws CASRuntimeException;

  /**
   * Get the document text.
   * 
   * @return The text being analyzed.
   */
  String getDocumentText();

  /**
   * Get the Sofa Data String (a.k.a. the document text).
   * 
   * @return The Sofa data string.
   */
  String getSofaDataString();

  /**
   * Sets the language for this document. This value sets the language feature of the special
   * instance of DocumentAnnotation associated with this CAS.
   * 
   * @param languageCode
   * @throws CASRuntimeException
   */
  void setDocumentLanguage(String languageCode) throws CASRuntimeException;

  /**
   * Gets the language code for this document from the language feature of the special instance of
   * the DocumentationAnnotation associated with this CAS.
   * 
   * @return language identifier
   */
  String getDocumentLanguage();

  /**
   * Set the Sofa data as an ArrayFS. Once set, Sofa data is immutable, and cannot be set again
   * until the CAS has been reset.
   * 
   * @param array
   *          The ArrayFS to be analyzed.
   * @param mime
   *          The mime type of the data
   * @exception CASRuntimeException
   *              If the Sofa data has already been set.
   */
  void setSofaDataArray(FeatureStructure array, String mime) throws CASRuntimeException;

  /**
   * Get the Sofa data array.
   * 
   * @return The Sofa Data being analyzed.
   */
  FeatureStructure getSofaDataArray();

  /**
   * Set the Sofa data as a URI. Once set, Sofa data is immutable, and cannot be set again until the
   * CAS has been reset.
   * 
   * @param uri
   *          The URI of the data to be analyzed.
   * @param mime
   *          The mime type of the data
   * @exception CASRuntimeException
   *              If the Sofa data has already been set.
   */
  void setSofaDataURI(String uri, String mime) throws CASRuntimeException;

  /**
   * Get the Sofa data array.
   * 
   * @return The Sofa Data being analyzed.
   */
  String getSofaDataURI();

  /**
   * Get the Sofa data as a byte stream.
   * 
   * @return A stream handle to the Sofa Data.
   */
  InputStream getSofaDataStream();

  /**
   * Get the mime type of the Sofa data being analyzed.
   * 
   * @return the mime type of the Sofa
   */
  String getSofaMimeType();
  
  /**
   * Add a feature structure to all appropriate indexes in the repository associated with this CAS
   * View.
   * 
   * <p>
   * <b>Important</b>: after you have called <code>addFsToIndexes(...)</code> on a FS, do not
   * change the values of any features used for indexing. If you do, the index will become corrupted
   * and may be unusable. If you need to change an index feature value, first call
   * {@link #removeFsFromIndexes(FeatureStructure) removeFsFromIndexes(...)} on the FS, change the
   * feature values, then call <code>addFsToIndexes(...)</code> again.
   * 
   * @param fs
   *          The Feature Structure to be added.
   * @exception NullPointerException
   *              If the <code>fs</code> parameter is <code>null</code>.
   */
  void addFsToIndexes(FeatureStructure fs);

  /**
   * Remove a feature structure from all indexes in the repository associated with this CAS View.
   * 
   * @param fs
   *          The Feature Structure to be removed.
   * @exception NullPointerException
   *              If the <code>fs</code> parameter is <code>null</code>.
   */
  void removeFsFromIndexes(FeatureStructure fs);

  /**
   * Get the standard annotation index.
   * 
   * @return The standard annotation index.
   */
  AnnotationIndex<Annotation> getAnnotationIndex();

  /**
   * Get the standard annotation index restricted to a specific annotation type.
   * 
   * @param type
   *          The annotation type the index is restricted to.
   * @return The standard annotation index, restricted to <code>type</code>.
   */
  AnnotationIndex<Annotation> getAnnotationIndex(Type type) throws CASRuntimeException;

  /**
   * Get the standard annotation index restricted to a specific annotation type.
   * 
   * @param type
   *          The annotation type the index is restricted to, 
   *          passed as an integer using the form
   *          MyAnnotationType.type
   * @return The standard annotation index, restricted to <code>type</code>.
   */
  AnnotationIndex<Annotation> getAnnotationIndex(int type) throws CASRuntimeException;

  /**
   * Get iterator over all views in this JCas.  Each view provides access to Sofa data
   * and the index repository that contains metadata (annotations and other feature 
   * structures) pertaining to that Sofa.
   * 
   * @return an iterator which returns all views.  Each object returned by
   *   the iterator is of type JCas.
   */
  Iterator<JCas> getViewIterator() throws CASException;  
  
  /**
   * Get iterator over all views with the given name prefix.  Each view provides access to Sofa data
   * and the index repository that contains metadata (annotations and other feature 
   * structures) pertaining to that Sofa.
   * <p>
   * When passed the prefix <i>namePrefix</i>, the iterator will return all views who 
   * name is either exactly equal to <i>namePrefix</i> or is of the form 
   * <i>namePrefix</i><code>.</code><i>suffix</i>, where <i>suffix</i> can be any String.
   * 
   * @param localViewNamePrefix  the local name prefix, before any sofa name mapping 
   *   is done, for this view (note: this is the same as the associated Sofa name prefix).
   * 
   * @return an iterator which returns all views with the given name prefix.  
   *   Each object returned by the iterator is of type JCas.
   */
  Iterator<JCas> getViewIterator(String localViewNamePrefix) throws CASException;
}