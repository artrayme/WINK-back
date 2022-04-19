import os
import sys
from turtle import right
from xml.etree.ElementTree import ParseError

from httplib2 import ProxiesUnavailableError

from support_scripts.gwf_parser import GWFParser
from support_scripts.scs_writer import SCsWriter

class Gwf2SCs:

    GWF_FORMAT = '.gwf'
    SCS_FORMAT = '.scs'

    def __init__(self):
        self.errors = []

    def run(self, gwf_str):
        elements = {}
        error = Gwf2SCs.parse_gwf(gwf_str, elements)
        if error is None:
            errors, scs_str = self.convert_to_scs(elements)
            self.scs_str = scs_str
            if not len(errors) == 0:
                for error in errors:
                    self.log_error(error)
        else:
            self.log_error(error)
            return

    @staticmethod
    def parse_gwf(gwf_str, elements):
        try:
            gwf_parser = GWFParser(elements)
            return gwf_parser.parse(gwf_str)
        except (TypeError, ParseError) as e:
            return e

    @staticmethod
    def convert_to_scs(elements):
        if elements is not None:
            writer = SCsWriter()
            errors, scs_str = writer.write(elements)
            return errors, scs_str
        return None

    def log_error(self, error):
        self.errors.append(error)

    def check_status(self) -> bool:
        if len(self.errors) > 0:
            error_str = ""
            for error in self.errors:
                error_str+=str(error)
           
            print(error_str)
            return False
        else:
            print(self.scs_str)
            return True

if __name__ == "__main__":
    converter = Gwf2SCs()
    converter.run(sys.argv[1])
    if converter.check_status():
        sys.exit(0)
    else:
        sys.exit(1)
