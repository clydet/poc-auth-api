FROM centos:7

RUN yum update -y
RUN yum install -y epel-release && yum clean all

# Install AWS cli, jq, and psql
RUN yum install -y which python-devel python-pip curl unzip wget gcc-c++ make \
  && curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip" \
  && unzip awscli-bundle.zip \
  && ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws \
  && aws --version \
  && yum install -y jq groff maven openssl

RUN pip install --upgrade pip && pip -V

# node installation
RUN curl -sL https://rpm.nodesource.com/setup_10.x > nodeInstall.sh
RUN chmod +x nodeInstall.sh && ./nodeInstall.sh
RUN yum install -y nodejs
RUN node --version
RUN npm install -g serverless
RUN npm install serverless-aws-documentation

# remove this directory so we can map it later
RUN rm -rf /root/.aws

# set a custom prompt for improved operational clarity and safety
RUN echo 'PS1="\[$(tput setaf 3)$(tput bold)[\]auth-poc@\\h$:\\w]#\[$(tput sgr0) \]"' >> /root/.bashrc

WORKDIR /auth-poc
ENTRYPOINT ["/bin/bash"]