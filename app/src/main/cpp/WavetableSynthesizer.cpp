#include "Log.h"
#include "Wavetable.h"
#include "WavetableSynthesizer.h"
#include <oboe/Oboe.h>

namespace soundsynthesizer {

    void WavetableSynthesizer::play() {
        LOGD("synthesizer playing");
        _isPlaying = true;

        std::lock_guard<std::mutex> lock(mLock);
        oboe::AudioStreamBuilder builder;
        oboe::Result result = builder.setSharingMode(oboe::SharingMode::Exclusive)
                ->setPerformanceMode(oboe::PerformanceMode::LowLatency)
                ->setChannelCount(channelCount)
                ->setSampleRate(sampleRate)
                ->setSampleRateConversionQuality(oboe::SampleRateConversionQuality::Medium)
                ->setFormat(oboe::AudioFormat::Float)
                ->setDataCallback(this)
                ->openStream(mStream);

        if (result != oboe::Result::OK) return;

        mStream->requestStart();
    }

    void WavetableSynthesizer::stop() {
        LOGD("synthesizer stopped");
        _isPlaying = false;

        std::lock_guard<std::mutex> lock(mLock);
        if (mStream) {
            mStream->stop();
            mStream->close();
            mStream.reset();
        }
    }

    bool WavetableSynthesizer::isPlaying() const {
        LOGD("synthesizer is playing method");
        return _isPlaying;
    }

    void WavetableSynthesizer::setFrequency(float frequencyInHz) {
        LOGD("synthesizer frequency: %.2f Hz", frequencyInHz);
    }

    void WavetableSynthesizer::setVolume(float volumeInDb) {
        LOGD("synthesizer volume: %.2f db", volumeInDb);
    }

    void WavetableSynthesizer::setWavetable(Wavetable wavetable) {
        LOGD("synthesizer wavetable selected: %.d", static_cast<int>(wavetable));
    }

    oboe::DataCallbackResult WavetableSynthesizer::onAudioReady(
            oboe::AudioStream *oboeStream,
            void *audioData,
            int32_t numFrames) {
        auto *floatData = (float *) audioData;

        for (int i = 0; i < numFrames; ++i) {
            float sampleValue = amplitude * sinf(mPhase);
            for (int j = 0; j < channelCount; j++) {
                floatData[i * channelCount + j] = sampleValue;
            }
            mPhase += mPhaseIncrement;
            if (mPhase >= kTwoPi) mPhase -= kTwoPi;
        }
        return oboe::DataCallbackResult::Continue;
    }
}
