import React, { Component } from 'react';
import { MDBDataTableV5 } from 'mdbreact';

class PhoneNumbers extends Component {
  componentDidMount() {
      fetch(
          "/phone")
          .then((res) => res.json())
          .then((customersPage) => {
              const phoneRecords = customersPage.content;
              phoneRecords.forEach(record => {
                if (record.countryNameIfAny == null)
                  record.countryNameIfAny = "-";
                record.valid = (record.valid == true) ? "Valid" : "Invalid";
              });

              this.setState({
                datatable: {
                  columns: this.columns,
                  rows: customersPage.content
                }
              });
          })
  }

  constructor(props) {
    super(props);
    this.state = {};
    this.columns = [
      {
        label: 'Customer Name',
        field: 'customerName',
        width: 150,
      },
      {
        label: 'Phone',
        field: 'phone',
        width: 150,
      },
      {
        label: 'Country Code',
        field: 'countryCode',
        width: 150,
      },
      {
        label: 'Country Name',
        field: 'countryNameIfAny',
        width: 150,
      },
      {
        label: 'State',
        field: 'valid',
        width: 150,
      },
    ];
  };

  render() {
    console.log(this.state);
    if (this.state.datatable != null)
      return <MDBDataTableV5 hover entriesOptions={[10, 20, 25]} entries={10} pagesAmount={10} data={this.state.datatable} fullPagination />;
    return <div></div>;
  }
}

export default PhoneNumbers;