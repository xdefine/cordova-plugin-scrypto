#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include "md5.h"
#include "sha1.h"

#ifdef __cplusplus
extern "C" {
#endif

	JNIEXPORT jstring JNICALL
	Java_net_xdefine_helper_SecurityHelper_genSecretToken(JNIEnv* env, jobject obj, jstring arg0, jstring arg1, jstring arg2) {

		try {
			const char* machine_name = env->GetStringUTFChars(arg0, NULL);
			const char* unique_data  = env->GetStringUTFChars(arg1, NULL);
			const char* generated_at = env->GetStringUTFChars(arg2, NULL);

			md5_state_t state;
			md5_byte_t digest[16];
			int di;
			int secret_pos;
			char secretTokenResult[16 * 2 + 1];

			char origin[100];
			sprintf(origin, "%s@%s@%s", machine_name, unique_data, generated_at);
			for (unsigned int i = 0; i < sizeof(origin); i++) {
				if (!origin[i]) {
					secret_pos = i;
					break;
				}
			}

			md5_init(&state);
			md5_append(&state, (const md5_byte_t *)origin, secret_pos);
			md5_finish(&state, digest);

			for (di = 0; di < 16; ++di) sprintf((secretTokenResult + di * 2), "%02x", digest[di]);

			char SECRET_KEYPASS[] = "a3fdx2kr5z";
			long sysTime = time(0);

			char timeByteString[100];
			sprintf(timeByteString, "%ld", sysTime);

			int lastIndex = 0;
			for (unsigned int i = 0; i < sizeof(timeByteString); i++) {
				if (!timeByteString[i]) {
					lastIndex = i;
					break;
				}

				const char key = timeByteString[i];
				timeByteString[i] = SECRET_KEYPASS[key - 48];
			}

			sprintf(timeByteString + lastIndex, "%s", secretTokenResult);

			char hex_output[16*2 + 1 + lastIndex + 1];

			md5_init(&state);
			md5_append(&state, (const md5_byte_t *)timeByteString, lastIndex + 32);
			md5_finish(&state, digest);

			for (di = 0; di < 16; ++di) sprintf((hex_output + di * 2) + lastIndex + 1, "%02x", digest[di]);
			for (int i = 0; i < lastIndex; i++) {
				hex_output[i] = timeByteString[i];
			}
			hex_output[lastIndex] = '-';

			jstring value = env->NewStringUTF(hex_output);

			delete machine_name;
			delete unique_data;
			delete generated_at;

			return value;
		}
		catch(...) {
		}
	}

	JNIEXPORT jstring JNICALL
	Java_net_xdefine_helper_SecurityHelper_genPassword(JNIEnv* env, jobject obj, jstring arg0) {

        const char* password = env->GetStringUTFChars(arg0, NULL);

        srand(time(NULL));

        char alphanum[] = "0123456789abcdefghijklmnopqrstuvwxyz";
        char rand_text[11];
        for (int i = 0; i < 10; ++i) sprintf(rand_text + i, "%c", alphanum[ rand() % (sizeof(alphanum) - 1) ]);

        SHA1 sha;
        unsigned message_digest[5];
        int pos = rand() % 30;

        sha.Reset();
        sha.Input(password, strlen(password));
        sha.Result(message_digest);

        char hash_password[41];
        sprintf(hash_password, "%08x%08x%08x%08x%08x", message_digest[0], message_digest[1], message_digest[2], message_digest[3], message_digest[4]);

        char encrypt_source[51];
        for (int i = 0; i < pos; i++) sprintf(encrypt_source + i, "%c", hash_password[i]);
        for (int i = 0; i < 10 ; i++) sprintf(encrypt_source + pos + i, "%c", rand_text[i]);
        for (int i = pos + 10; i < 51; i++) sprintf(encrypt_source + i, "%c", hash_password[i - 10]);

        sha.Reset();
        sha.Input(encrypt_source, 50);
        sha.Result(message_digest);

        char hash_result[41];
        sprintf(hash_result, "%08x%08x%08x%08x%08x", message_digest[0], message_digest[1], message_digest[2], message_digest[3], message_digest[4]);

        char result_text[53];
        sprintf(result_text, "%s%02d%s", hash_result, pos, rand_text);

        delete password;

        return env->NewStringUTF(result_text);
	}




#ifdef __cplusplus
}
#endif
