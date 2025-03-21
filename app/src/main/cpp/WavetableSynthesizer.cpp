#include "WavetableSynthesizer.h"
#include <cmath>
#include "Log.h"
#include "OboeAudioPlayer.h"
#include "WavetableOscillator.h"

namespace soundsynthesizer {

    float dbToAmplitude(float db) {
        return std::pow(10.f, db / 20.f);
    }

    WavetableSynthesizer::WavetableSynthesizer()
        : _oscillator{ std::make_shared<WavetableOscillator>(_wavetableFactory.getWaveTable(_currentWavetable), samplingRate) },
          _audioPlayer{std::make_unique<OboeAudioPlayer>(_oscillator, samplingRate)} {}

    WavetableSynthesizer::~WavetableSynthesizer() = default;

    bool WavetableSynthesizer::isPlaying() const {
        LOGD("synthesizer is playing method");
        return _isPlaying;
    }

    void WavetableSynthesizer::play() {
        LOGD("synthesizer playing");
        std::lock_guard<std::mutex> lock(_mutex);
        const auto result = _audioPlayer->play();
        if (result == 0) {
            _isPlaying = true;
        } else {
            LOGD("Could not start playback.");
        }
    }

    void WavetableSynthesizer::setFrequency(float frequencyInHz) {
        LOGD("synthesizer frequency: %.2f Hz", frequencyInHz);
        _oscillator->setFrequency(frequencyInHz);
    }

    void WavetableSynthesizer::setVolume(float volumeInDb) {
        LOGD("synthesizer volume: %.2f db", volumeInDb);
        const auto amplitude = dbToAmplitude(volumeInDb);
        _oscillator->setAmplitude(amplitude);
    }

    void WavetableSynthesizer::setWavetable(Wavetable wavetable) {
        LOGD("synthesizer wavetable selected: %.d", static_cast<int>(wavetable));
        if (_currentWavetable != wavetable) {
            _currentWavetable = wavetable;
            _oscillator->setWavetable(_wavetableFactory.getWaveTable(wavetable));
        }
    }

    void WavetableSynthesizer::stop() {
        LOGD("synthesizer stopped");
        std::lock_guard<std::mutex> lock(_mutex);
        _audioPlayer->stop();
        _isPlaying = false;
    }
}
