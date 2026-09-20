# Dockerfile untuk Automated Testing & Build Android (SI-SANTRI)
FROM eclipse-temurin:17-jdk-jammy

ENV ANDROID_SDK_ROOT="/opt/android-sdk"
ENV ANDROID_HOME="/opt/android-sdk"
ENV PATH="${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools"

# Install dependensi sistem dasar
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# Install Android Commandline Tools & SDK Platform 36
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    curl -o cmdline-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip && \
    unzip cmdline-tools.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/latest && \
    rm cmdline-tools.zip

# Terima lisensi Android SDK & pasang komponen SDK
RUN yes | sdkmanager --licenses && \
    sdkmanager "platforms;android-36" "build-tools;35.0.0" "platform-tools"

WORKDIR /workspace

# Salin file proyek
COPY . .

# Berikan izin eksekusi gradle jika ada wrapper
RUN if [ -f "./gradlew" ]; then chmod +x ./gradlew; fi

# Perintah default menjalankan unit test & Robolectric test suite
CMD ["gradle", ":app:testDebugUnitTest", "--no-daemon"]
