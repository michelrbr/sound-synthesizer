#pragma once

#include <memory>
#include <mutex>
#include "Wavetable.h"
#include <oboe/Oboe.h>

namespace soundsynthesizer {

    class WavetableSynthesizer: public oboe::AudioStreamDataCallback {
    public:
        void play();

        void stop();

        bool isPlaying() const;

        void setFrequency(float frequencyInHz);

        void setVolume(float volumeInDb);

        void setWavetable(Wavetable wavetable);

        oboe::DataCallbackResult onAudioReady(
                oboe::AudioStream *oboeStream,
                void *audioData,
                int32_t numFrames) override;

    private:
        std::atomic<bool> _isPlaying = false;

        std::mutex mLock;
        std::shared_ptr<oboe::AudioStream> mStream;

        static int constexpr channelCount = 2;
        static int constexpr sampleRate = 48000;
        static float constexpr amplitude = 0.5f;
        static float constexpr frequency = 440;
        static float constexpr kTwoPi = M_PI * 2;
        static double constexpr mPhaseIncrement = frequency * kTwoPi / (double) sampleRate;
        float mPhase = 0.0;
    };
}
