#!/usr/bin/env python3
"""
@author: Zsolt Kovari, Georg Hinkel

"""
import argparse
import os
import shutil
import subprocess
try:
    import ConfigParser
except ImportError:
    import configparser as ConfigParser
import json
from subprocess import CalledProcessError

BASE_DIRECTORY = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
print("Running benchmark with root directory " + BASE_DIRECTORY)


class JSONObject(object):
    def __init__(self, d):
        self.__dict__ = d


def build(conf, skip_tests=False):
    """
    Builds all solutions
    """
    for tool in conf.Tools:
        config = ConfigParser.ConfigParser()
        config.read(os.path.join(BASE_DIRECTORY, "solutions", tool, "solution.ini"))
        set_working_directory("solutions", tool)
        if skip_tests:
            subprocess.check_call(config.get('build', 'skipTests'), shell=True)
        else:
            subprocess.check_call(config.get('build', 'default'), shell=True)


def benchmark(conf):
    """
    Runs measurements
    """
    header = os.path.join(BASE_DIRECTORY, "output", "header.csv")
    result_file = os.path.join(BASE_DIRECTORY, "output", "output.csv")
    if os.path.exists(result_file):
        with open(result_file, "a") as file:
            # append a separator line
            from datetime import datetime
            file.write('-' * 30 + ' New measurement started at ' + datetime.now().isoformat() + ' ' + '-' * 30 + '\n')
    else:
        shutil.copy(header, result_file)
        # os.remove(result_file)
    os.environ['Runs'] = str(conf.Runs)

    # by default we test every model for every tool
    available_models = { tool : conf.Models for tool in conf.Tools }

    for r in range(0, conf.Runs):
        os.environ['RunIndex'] = str(r)
        print("## Run {}".format(r))

        for tool in conf.Tools:
            config = ConfigParser.ConfigParser()
            config.read(os.path.join(BASE_DIRECTORY, "solutions", tool, "solution.ini"))
            set_working_directory("solutions", tool)
            os.environ['Tool'] = tool

            print('# Available models for {tool} are {models}'.format(tool=tool, models=available_models[tool]))

            for model in available_models[tool]:
                full_model_path = os.path.abspath(os.path.join(BASE_DIRECTORY, "models", model))
                os.environ['Model'] = model
                os.environ['ModelPath'] = full_model_path
                print("Running benchmark: tool = " + tool + ", model = " + full_model_path)

                try:
                    output = subprocess.check_output(config.get('run', 'cmd'), shell=True, timeout=conf.Timeout)
                    with open(result_file, "ab") as file:
                        file.write(output)
                except CalledProcessError as e:
                    print("Program exited with error")
                    print('stdout:')
                    print(e.output)
                    print('stderr:')
                    print(e.stderr)
                except subprocess.TimeoutExpired as e:
                    print("Program reached the timeout set ({0} seconds). The command we executed was '{1}'".format(e.timeout, e.cmd))

                    # if a tool failed running some model we remove that model as well as all "stronger" models
                    failed_model_idx = conf.Models.index(model)
                    available_models[tool] = conf.Models[ : failed_model_idx]
                    print('# Ignoring models {models} for tool {tool} in the future'.format(models=conf.Models[ : failed_model_idx], tool=tool))


def clean_dir(*path):
    dir = os.path.join(BASE_DIRECTORY, *path)
    if os.path.exists(dir):
        shutil.rmtree(dir)
    os.mkdir(dir)


def set_working_directory(*path):
    dir = os.path.join(BASE_DIRECTORY, *path)
    os.chdir(dir)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-b", "--build",
                        help="build the project",
                        action="store_true")
    parser.add_argument("-m", "--measure",
                        help="run the benchmark",
                        action="store_true")
    parser.add_argument("-s", "--skip-tests",
                        help="skip JUNIT tests",
                        action="store_true")
    parser.add_argument("-e", "--extract",
                        help="extract results",
                        action="store_true")
    parser.add_argument("-t", "--test",
                        help="run test",
                        action="store_true")
    parser.add_argument("-d", "--debug",
                        help="set debug to true",
                        action="store_true")
    args = parser.parse_args()

    set_working_directory("config")
    with open("config.json", "r") as config_file:
        config = json.load(config_file, object_hook=JSONObject)

    if args.debug:
        os.environ['Debug'] = 'true'
    if args.build:
        build(config, args.skip_tests)
    if args.measure:
        benchmark(config)
    if args.test:
        build(config, False)

    # if there are no args, execute a full sequence
    # with the test and the visualization/reporting
    no_args = all(val is False for val in vars(args).values())
    if no_args:
        build(config, False)
        benchmark(config)
