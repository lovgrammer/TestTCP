#include <jni.h>
#include <string>
#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include "client.h"

/**
 *
 * @param env - reference to the JNI environment
 * @param jStr - incoming jstring
 * @return - outputs a proper C++ string
 */
std::string jstring2string(JNIEnv *env, jstring jStr) {
  if (!jStr)
    return "";

  const jclass stringClass = env->GetObjectClass(jStr);
  const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
  const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
								     env->NewStringUTF("UTF-8"));

  size_t length = (size_t) env->GetArrayLength(stringJbytes);
  jbyte *pBytes = env->GetByteArrayElements(stringJbytes, NULL);

  std::string ret = std::string((char *) pBytes, length);
  env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

  env->DeleteLocalRef(stringJbytes);
  env->DeleteLocalRef(stringClass);
  return ret;
}

extern "C" {

  /**
   *
   * @param env - reference to the JNI environment
   * @param instance - reference to *this* Java object
   * @param hostname - the IP address of the remote server
   * @param port  - the port to connect to on the remote server
   * @return  - returns a socket to be stored and used or -1 for error
   */
  JNIEXPORT jint JNICALL
  Java_snu_nxc_testtcp_ClientAgent_init(JNIEnv *env,
						  jobject instance,
						  jstring hostname,
						  jint port,
						  jstring savename,
						  jstring interfacename) {
    sclient k;
    string hname = jstring2string(env, hostname);
    string sname = jstring2string(env, savename);
    string iname = jstring2string(env, interfacename);
    // system("su -c rm -rf /sdcard/trace.pcap");
    system("kill -9 `ps | grep su | awk '{print $2}'`");
    char cmd[200];
    sprintf(cmd, "su -c tcpdump -ttt -w /sdcard/trace_%s.pcap -i %s dst %s and dst port %d &", sname.c_str(), iname.c_str(), hname.c_str(), port);
    system(cmd);
    return k.connectToServer(hname, (int) port);
  }

  /**
   *
   * @param env - reference to the JNI environment
   * @param instance - reference to *this* Java object
   * @param sock - active socket to send data through
   * @param data - data to be sent to the remote server
   */
  JNIEXPORT void JNICALL
  Java_snu_nxc_testtcp_ClientAgent_sendData(
						       JNIEnv *env,
						       jobject instance,
						       jint sock,
						       jstring data) {
    sclient k;
    string sData = jstring2string(env, data);
    k.sendData((int) sock, sData);
  }

  /**
   *
   * @param env - reference to the JNI environment
   * @param instance - reference to *this* Java object
   * @param sock - active socket to disconnect
   */
  JNIEXPORT void JNICALL
  Java_snu_nxc_testtcp_ClientAgent_disconnect(JNIEnv *env,
							 jobject instance,
							 jint sock) {
    sclient k;
    k.disconnectFromServer((int) sock);
    system("kill -9 `ps | grep su | awk '{print $2}'`");
  }

  /**
   *
   * @param env - reference to the JNI environment
   * @param instance - reference to *this* Java object
   * @param sock - active socket to listen for incoming data from
   * @return - returns incoming data from remote server
   */
  JNIEXPORT jstring JNICALL
  Java_snu_nxc_testtcp_ClientAgent_recvData(JNIEnv *env,
						       jobject instance,
						       jint sock) {
    sclient k;
    string data = k.recvData((int) sock);
    return env->NewStringUTF(data.c_str());
  }
}
