# 🌐 InternetBox Reboot Bot

## Overview 📡
InternetBox Reboot Bot is a tool designed to monitor your internet connectivity 🌍 and automatically perform recovery actions if a connection loss is detected. This application is vital for maintaining continuous internet access and can be used in environments where stable connectivity is crucial.

## Features 🌟
- **Connectivity Monitoring** 🖥️: Constantly checks connectivity by accessing multiple pre-defined URLs.
- **Automated Recovery** 🔧: Automatically restarts your internet router or performs other configured actions to restore connectivity.
- **Flexible Configuration** ⚙️: Users can define specific websites, monitoring intervals, and recovery steps through a configuration file.

## Getting Started 🚀

### Prerequisites
- Java 21 or later for running the jar file.
- Docker if you prefer to run it as a Docker container.

### Using the Jar File 🏺
1. Download the latest version of the `internetbox-reboot-bot.jar` file from the releases page.
2. Run the application using the following command:
   ```bash
   java -jar internetbox-reboot-bot.jar --config path/to/your/config.yml
   ```

### Using the Docker Image 🐳

Either use docker directly

1. Pull the Docker image from Docker Hub:
   ```bash
   docker pull simschla/internetbox-reboot-bot:latest
   ```
2. Run the Docker container, ensuring to mount the configuration file and provide necessary passwords:
   ```bash
   docker run -v /path/to/your/config.yml:/app/config.yml \
     -e INTERNETBOX_PASSWORD=your-internetbox-password \
     -e TPLINK_PASSWORD=your-tplink-password \
     simschla/internetbox-reboot-bot
   ```

Or use docker-compose

1. Create a `docker-compose.yml` file with the following content:
   ```yaml
   services:
     internetbox-reboot-bot:
       image: simschla/internetbox-reboot-bot:latest
       volumes:
         - /path/to/your/config.yml:/app/config.yml
   ```

2. Create a `.env` file containing the necessary passwords:
   ```env
   INTERNETBOX_PASSWORD=your-internetbox-password
   TPLINK_PASSWORD=your-tplink-password
   ```
   
3. Run the Docker container using Docker Compose (ensure the `.env` file is in the same directory). Use necessary
   params to make sure the image is pulled freshly if needed.
   ```bash
   docker-compose pull
   docker-compose up --build --detach
   ```

## Configuration ⚙️
To configure the InternetBox Reboot Bot, create a YAML file based on the following example structure:

```yaml
driver:
  type: delayed-retry
  timeBetweenChecks: 10
  timeUnit: MINUTES
  maxRetries: 5
networkChecker:
  type: failAfter
  seconds: 300
  from:
    type: majority
    of:
      - type: url
        url: https://www.google.com
      - type: url
        url: https://www.github.com
      - type: url
        url: https://www.facebook.com
      - type: url
        url: https://www.microsoft.com
        httpMethod: GET
rebootActor:
  type: sequential
  of:
    - type: internet-box
      url: https://192.168.1.1
    - type: wait
      seconds: 120
    - type: tp-link-switch
      url: https://192.168.1.2
    - type: wait
      seconds: 20
```

## Supported Devices 📶
Currently, the application supports the following devices for automated rebooting:
- Swisscom Internet-Box 3 [More Info](https://www.swisscom.ch/en/residential/products/internetrouter/details.html/internet-box-3-ip-11039000)
- TP-Link Ethernet Switch TL-SG116E [More Info](https://www.tp-link.com/de/business-networking/easy-smart-switch/tl-sg116e/)

## Contributing 🤝
We welcome contributions, especially for adding support for more devices. Please fork the repository, make your changes, and submit a pull request for review.

## License 📜
This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.
