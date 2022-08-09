#include <jni.h>
#include <string>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_liwei_nativelib_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    printf("213");

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_liwei_nativelib_NativeLib_sayHello(JNIEnv * env, jobject thiz, jstring jstr) {
    const char * c_str = NULL;
    char buf[128] = {0};
    jboolean iscopy;
    c_str = (*env).GetStringUTFChars(jstr, &iscopy);
    printf("213");
    if(c_str == NULL) {
        return NULL;
    }
    sprintf(buf, "hello , 世界 %s", c_str);
    (*env).ReleaseStringUTFChars(jstr, c_str);
    return (*env).NewStringUTF(buf);
}

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_liwei_nativelib_NativeLib_sayHello(JNIEnv * env, jobject jclaz, jstring jstr) {
//    const char * c_str = NULL;
//    char buf[128] = {0};
//    jboolean iscopy;
//    c_str = (*env).GetStringUTFChars(jstr, &iscopy);
//    printf("isCopy:%d\n", iscopy);
//    if(c_str == NULL) {
//        return NULL;
//    }
//    printf("C_str: %s \n", c_str);
//    sprintf(buf,"Hello, 你好 %s", c_str);
//    printf("C_str: %s \n", buf);
//    (*env).ReleaseStringUTFChars(jstr, c_str);
//    return (*env).NewStringUTF(buf);
//}

int m_fd;
int32_t m_size;
int8_t  *m_ptr;

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_liwei_nativelib_NativeLib_putString(JNIEnv *env, jobject thiz, jstring msg) {
//    android
    std::string file = "/sdcard/.test.txt";
    m_fd = open(file.c_str(), O_RDWR | O_CREAT, S_IRWXU);
    m_size = getpagesize();
    ftruncate(m_fd, m_size*2);
    // m_size:映射区的长度。 需要是整数页个字节    byte[]
    m_ptr = (int8_t *) mmap(0, m_size, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd,
                            0);

    std::string data("21312313");
    //将 data 的 data.size() 个数据 拷贝到 m_ptr
    //Java 类似的：
//        byte[] src = new byte[10];
//        byte[] dst = new byte[10];
//        System.arraycopy(src, 0, dst, 0, src.length);
    memcpy(m_ptr, data.data(), data.size());


//    munmap();
//    ftruncate(m_fd, m_size*2);
//    m_ptr = (int8_t *) mmap(0, m_size*2, PROT_READ | PROT_WRITE, MAP_SHARED, m_fd,
//                            0);


    __android_log_print(ANDROID_LOG_ERROR, "mmap", "写入数据:%s", data.c_str());
    return true;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_liwei_nativelib_NativeLib_getString(JNIEnv *env, jobject thiz) {
//申请内存
    char *buf = static_cast<char *>(malloc(100));

    memcpy(buf, m_ptr, 100);

    std::string result(buf);
    __android_log_print(ANDROID_LOG_ERROR, "MMKV", "读取数据:%s", result.c_str());

    //取消映射
    munmap(m_ptr, m_size);
    //关闭文件
    close(m_fd);
    return env->NewStringUTF(result.c_str());
}