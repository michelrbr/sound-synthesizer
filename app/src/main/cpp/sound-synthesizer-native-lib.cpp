#include <jni.h>
#include <memory>
#include "Log.h"
#include "WavetableSynthesizer.h"

extern "C" {
    JNIEXPORT jlong JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_create(
            JNIEnv *env,
            jobject thiz) {

        auto synthesizer = std::make_unique<soundsynthesizer::WavetableSynthesizer>();

        if (not synthesizer) {
            LOGD("Failed to create the synthesizer.");
            synthesizer.reset(nullptr);
        }

        LOGD("Create the synthesizer.");

        return reinterpret_cast<jlong>(synthesizer.release());
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_delete(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);

        if (not synthesizer) {
            LOGD("Failed to create the synthesizer.");
            return;
        }

        LOGD("Delete the synthesizer.");
        delete synthesizer;
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_play(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);

        if (synthesizer) {
            synthesizer->play();
        } else {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
        }
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_stop(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);

        if (synthesizer) {
            synthesizer->stop();
        } else {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
        }
    }

    JNIEXPORT jboolean JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_isPlaying(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);

        if (not synthesizer) {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
            return false;
        }
        return synthesizer->isPlaying();
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_setFrequency(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle,
            jfloat frequency_in_hz) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);
        const auto nativeFrequency = static_cast<float>(frequency_in_hz);

        if (synthesizer) {
            synthesizer->setFrequency(nativeFrequency);
        } else {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
        }
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_setVolume(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle,
            jfloat amplitude_in_db) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);
        const auto nativeFVolume = static_cast<float>(amplitude_in_db);

        if (synthesizer) {
            synthesizer->setVolume(nativeFVolume);
        } else {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
        }
    }

    JNIEXPORT void JNICALL
    Java_br_com_michel_soundsynthesizer_domain_SynthesizerBridgeImpl_setWavetable(
            JNIEnv *env,
            jobject thiz,
            jlong synthesizer_handle,
            jint wavetable) {

        auto* synthesizer = reinterpret_cast<soundsynthesizer::WavetableSynthesizer*>(synthesizer_handle);
        const auto nativeWavetable = static_cast<soundsynthesizer::Wavetable>(wavetable);

        if (synthesizer) {
            synthesizer->setWavetable(nativeWavetable);
        } else {
            LOGD("Synthesizer not created. Please, create the synthesizer first by calling create().");
        }
    }
}
